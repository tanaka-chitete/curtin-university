classdef ctm_plane
    %CTM_PLANE Plane class for Curtin Maths dept
    %
    % The general equation of a plane is given by:
    %     a*x + b*y + c*z + d = 0
    %
    % where the vector:  n = [a,b,c] is NORMAL to the plane
    %
    % If point p0 = [x0,y0,z0] is a point on then plane, then we know that:
    %     d = -a*x0  +  -b*y0  +  -c*z0
    %----------------------------------------------------------------------
    % USAGE SYNTAX:
    % PL = ctm_plane('PL1', {ptA, ptB, ptC} )
    % PL = ctm_plane('PL1', {pt0, n_norm_vec_NUM} )
    % PL = ctm_plane('PL1', 2*x+4*y-7*z==9,[x,y,z] ) 
    %----------------------------------------------------------------------
    % EXAMPLE USAGE - syntax #1:
    %   pntA = ctm_point('P1', 1, 2, 3 )
    %   pntB = ctm_point('PB', 4, 5, 6 )
    %   pntC = ctm_point('PB', 7, 8, 9 )
    %
    %   PL   = ctm_plane('PL1', {pntA, pntB, pntC} )
    %----------------------------------------------------------------------
    % EXAMPLE USAGE - syntax #2:
    %   pnt0         = ctm_point('P0', 1, 2, 3 )
    %   a_NORMAL_vec = [7,8,9]
    %   L            = ctm_plane('L1', {pnt0, a_NORMAL_vec} )
    %----------------------------------------------------------------------    
    % EXAMPLE USAGE - syntax #3:
    %   syms x y z
    %   PL = ctm_plane('PL1', 2*x+4*y-7*z==6,[x,y,z] )     
    %----------------------------------------------------------------------    
    % DEMO example:
    %   bh_intro_to_POINTS_LINES_PLANES
    %----------------------------------------------------------------------
    % ATTENTION:
    %   Requires the GEOM3D toolbox - it's free
    %----------------------------------------------------------------------
    
    properties (Access = protected)
        label_str        = '';
        form_str         = 'ctm_plane';
                                %      a     b      c     d 
        sym_eqn_of_plane = [];  % eg:  3*x + -7*y + 3*z + 88
        the_sym_var_list = [sym('x'), sym('y'), sym('z')];
        n_normal_row     = sym([0,0,0]);
        d_val            = sym(0);
        
        dir_line_1;    % a line  on the plane (aka a direction vector)    
        dir_line_2;    % a line  on the plane (aka a direction vector)   
        pnt_on_plane;  % a point on the plane
        
        geom3d_plane_row_DBL = [];       
    end
    
    methods
        function OBJ = ctm_plane(label_str, varargin)
            % PL = ctm_plane('PL1', {ptA, ptB, ptC} )
            % PL = ctm_plane('PL1', {pt0, n_norm_vec_NUM} )
            % PL = ctm_plane('PL1', 2*x+4*y-7*z==9,[x,y,z] ) 
            
            % some initial argument checking
            validateattributes(label_str,{'char'},{'vector'});
            
            % OK: get on with the job
            OBJ.label_str   = label_str;
            
            if( 2 == nargin )
                some_CE = varargin{1};
                 if( isa(some_CE{1},'ctm_point') &&  ...
                     isa(some_CE{2},'ctm_point') && ...
                     isa(some_CE{3},'ctm_point') )
                     % PL = ctm_plane('PL1', {ptA, ptB, ptC} )
                     
                     pt1 = some_CE{1};
                     pt2 = some_CE{2};
                     pt3 = some_CE{3};
                     
                     v_12 = pt2 - pt1;
                     v_13 = pt3 - pt1;
                     n_row = cross( v_12.get_coords_row(), ...
                                    v_13.get_coords_row()   );
                     n_row = double(n_row);
                     
                     OBJ = ctm_plane(label_str, {pt1, n_row});
                     
                 elseif( isa(some_CE{1}, 'ctm_point') &&  ...
                         isa(some_CE{2}, 'numeric')   &&  ...
                         3==numel(some_CE{2})  )
                     % PL = ctm_plane('PL1', {pt0, n_norm_vec_NUM} )
                     
                     pt0          = some_CE{1};
                     norm_n_vec   = some_CE{2};
                     norm_n_vec   = sym(  norm_n_vec  );
                     d            = sum( -1*norm_n_vec .* pt0.get_coords_row() );
                     xyz_sym_list = OBJ.the_sym_var_list;           
                     
                     OBJ.d_val            = d ;
                     OBJ.n_normal_row     = sym( [norm_n_vec(:)].' );
                     OBJ.sym_eqn_of_plane = sum( norm_n_vec .* xyz_sym_list) + d;
                 else
                     error('ERROR:  Unknown calling syntax');    
                 end
            elseif( 3==nargin )
                % PL = ctm_plane('PL1', 2*x+4*y-7*z==9,[x,y,z] )  
                INP_EQIV_EQ     = varargin{1};
                INP_symvar_list = varargin{2};

                validateattributes(INP_EQIV_EQ,    {'sym'},{'scalar'});
                validateattributes(INP_symvar_list,{'sym'},{'vector','numel',3});
                
                % just make the symvar list a row
                INP_symvar_list = [INP_symvar_list(:)].'; 
             
                % assert that we've only been given expressions with 3
                % unique symbols                
                assert(3==length(symvar(INP_symvar_list)));               
                
                % OK: get on with the job
                OBJ.the_sym_var_list  = INP_symvar_list; % a ROW                
                
                [norm_n_row, d] =  LOC_parse_EQ_INP( INP_EQIV_EQ, INP_symvar_list );
                
                 OBJ.d_val            = d;
                 OBJ.n_normal_row     = norm_n_row;
                 OBJ.sym_eqn_of_plane = sum( norm_n_row .* OBJ.the_sym_var_list) + d;
            else
                error('ERROR: UNknown usage mode !');
            end
            
            % LAST step:
            %  create a geom3d plane row use the GEOM3D toolbox
            %   The created plane data has the following format:
            %   PLANE = [X0 Y0 Z0  DX1 DY1 DZ1  DX2 DY2 DZ2], with
            %   - (X0, Y0, Z0) is a point belonging to the plane
            %   - (DX1, DY1, DZ1) is a first direction vector
            %   - (DX2, DY2, DZ2) is a second direction vector            
            
            a_pnt        = LOC_calc_point_on_plane(OBJ.n_normal_row, OBJ.d_val);
            p0_cords_DBL = double(a_pnt.get_coords_row());
            n_vec_DBL    = double(OBJ.n_normal_row);

            OBJ.geom3d_plane_row_DBL = createPlane(p0_cords_DBL, n_vec_DBL);
            
            OBJ.dir_line_1   = ctm_line('L1', {a_pnt, OBJ.get_V1_row_from_geo} );
            OBJ.dir_line_2   = ctm_line('L2', {a_pnt, OBJ.get_V2_row_from_geo} );
            OBJ.pnt_on_plane = a_pnt;
        end
        %==================================================================
        function R = get_geo_row_from_createPlane(OBJ)
                 R = OBJ.geom3d_plane_row_DBL;
        end
        %==================================================================
        function V1 = get_V1_row_from_geo(OBJ)
                 V1 = OBJ.geom3d_plane_row_DBL(4:6);
        end
        %==================================================================
        function V2 = get_V2_row_from_geo(OBJ)
                 V2 = OBJ.geom3d_plane_row_DBL(7:9);
        end
        %==================================================================
        function display(OBJ)
           eqn_str = '';
           for kk=1:length(OBJ.the_sym_var_list)
               tmp_str = sprintf('%s*%s + ', ...
                                 char(OBJ.n_normal_row(kk)), ...
                                 char(OBJ.the_sym_var_list(kk)) );
               eqn_str = [eqn_str, tmp_str];              
           end
           eqn_str = [eqn_str, sprintf('%s',OBJ.d_val) ];
           eqn_str = [eqn_str,'==0'];
           fprintf('\n label of object        %s', OBJ.label_str);
           fprintf('\n form of object         %s', OBJ.form_str);
           fprintf('\n equation of the plane  %s', eqn_str);
           fprintf('\n')
        end
        %==================================================================
         function detail(OBJ)
                  display(OBJ)
                  
                  p0_dat = simplify( OBJ.pnt_on_plane.get_coords_row()  );
                  V1_dat = simplify( OBJ.dir_line_1.get_dir()           );
                  V2_dat = simplify( OBJ.dir_line_2.get_dir()           );
                  
                  LOC_echo_p0_V1_v2(p0_dat, V1_dat, V2_dat);
         end
        %==================================================================
        function form(OBJ)
           fprintf('\n %s',OBJ.form_str);
           fprintf('\n')            
        end
        %==================================================================
        function plot(OBJ)
            n_num_vec = double(OBJ.n_normal_row);
            
            THE_EQ = OBJ.sym_eqn_of_plane;
            
            syms x y z
            
            THE_EQ = subs(THE_EQ, OBJ.the_sym_var_list, [x y z]);
            
            
            if(n_num_vec(3) ~= 0)
                % OK:  our Z coeff is NOT zero, so we have an eqN like
                %      this:  3*z + ?*x + ?*y + ? ==0
               f_xy =  solve(THE_EQ==0, z);
               %fsurf(f_xy, 'EdgeColor', 'none');
               fsurf(f_xy, 'MeshDensity' , 10);
               %axis('equal')
            else
                if( n_num_vec(1) ~= 0 && n_num_vec(2) ~= 0)
                    % OK:  our X coeff is NOT zero, AND Y coeff is NOT zero
                    %      so we have an eqN like this:  2*x + 3*y + ? ==0                
                    yf_x    = solve(THE_EQ==0, y);
                    ML_yf_x = matlabFunction(yf_x);

                    [XX, ZZ] = meshgrid(-5:0.5:5, -5:0.5:5);

                    YY = ML_yf_x(XX);
                    surf(XX, YY, ZZ, 'EdgeColor', 'none');
                
                elseif(n_num_vec(1) == 0)
                    % OK:  our X coeff IS zero, AND Y coeff is NOT zero
                    %      so we have an eqN like this: 3*y + ? ==0   
                    y_val    = solve(THE_EQ==0, y);
                    y_val    = double( y_val );
                    
                    [XX, ZZ] = meshgrid(-5:0.5:5, -5:0.5:5);
                    YY       = y_val * ones(size(XX));
                    surf(XX, YY, ZZ); %, 'EdgeColor', 'none');
                else
                    % OK:  our Y coeff IS zero, AND X coeff is NOT zero
                    %      so we have an eqN like this: 3*x + ? ==0   
                    x_val    = solve(THE_EQ==0, x);
                    x_val    = double( x_val );
                    
                    [YY, ZZ] = meshgrid(-5:0.5:5, -5:0.5:5);
                    XX       = x_val * ones(size(YY));
                    surf(XX, YY, ZZ); %, 'EdgeColor', 'none');
                end       
            end
            
            xlabel(gca, char(OBJ.the_sym_var_list(1)), 'FontWeight', 'Bold' )
            ylabel(gca, char(OBJ.the_sym_var_list(2)), 'FontWeight', 'Bold' )
            zlabel(gca, char(OBJ.the_sym_var_list(3)), 'FontWeight', 'Bold' )
            
            % now plot the NORMAL vector
            hold(gca,'on')
            
            % get limits of the surfcae plot
            lims_vec = [xlim(gca), ylim(gca), zlim(gca) ];
            lims_vec = abs(lims_vec);
            scaling_term = 1.5*max(lims_vec);
            
            norm_vec    = double(OBJ.n_normal_row);
            norm_vec    = scaling_term*norm_vec/norm(norm_vec);
            %pnt_sta     = [0,0,0];
            pnt_sta     = double( OBJ.pnt_on_plane.get_coords_row()  );
            pnt_end     = pnt_sta + norm_vec;
            
            plot3([pnt_sta(1),  pnt_end(1)], ...
                  [pnt_sta(2),  pnt_end(2)], ...
                  [pnt_sta(3),  pnt_end(3)], '-r', 'LineWidth',5);
              
            pnt_end     = pnt_sta - norm_vec;
            plot3([pnt_sta(1), pnt_end(1)], ...
                  [pnt_sta(2), pnt_end(2)], ...
                  [pnt_sta(3), pnt_end(3)], '-r', 'LineWidth',5);
              axis('equal')
        end % plot()
        %==================================================================
        function plot_distance(OBJ, some_thing)
            the_dist = distance(OBJ,some_thing)
            if( isequal(the_dist, sym(NaN)) )
                return
            end
            draw_normals(OBJ, the_dist);
        end
        %==================================================================
        function draw_normals(OBJ, mag_of_normal)
            % some initial argument checking
            validateattributes(mag_of_normal,{'numeric','sym'},{'scalar'});
            if(isa(mag_of_normal,'sym'))
                mag_of_normal = double(mag_of_normal);
            end
            
            norm_vec    = double(OBJ.n_normal_row);
            norm_vec    = mag_of_normal*norm_vec/norm(norm_vec);
            p0_DBL      = double( OBJ.pnt_on_plane.get_coords_row()  );
            
            NUM_TO_DRAW = 5;
            for kk=1:NUM_TO_DRAW
                V1_DBL = OBJ.get_V1_row_from_geo();
                V2_DBL = OBJ.get_V2_row_from_geo();
                V1_DBL = V1_DBL/norm(V1_DBL);
                V2_DBL = V2_DBL/norm(V2_DBL);
                
                pnt_sta = p0_DBL + kk*V1_DBL;
                pnt_end = pnt_sta + norm_vec;
            
                plot3([pnt_sta(1),  pnt_end(1)], ...
                      [pnt_sta(2),  pnt_end(2)], ...
                      [pnt_sta(3),  pnt_end(3)], '-g', 'LineWidth',5);
                pnt_sta = p0_DBL + kk*V1_DBL;
                pnt_end = pnt_sta - norm_vec;
                plot3([pnt_sta(1),  pnt_end(1)], ...
                      [pnt_sta(2),  pnt_end(2)], ...
                      [pnt_sta(3),  pnt_end(3)], '-y', 'LineWidth',5);
                  
                pnt_sta = p0_DBL + kk*V2_DBL;
                pnt_end = pnt_sta + norm_vec;
                plot3([pnt_sta(1),  pnt_end(1)], ...
                      [pnt_sta(2),  pnt_end(2)], ...
                      [pnt_sta(3),  pnt_end(3)], '-m', 'LineWidth',5);
                pnt_sta = p0_DBL + kk*V2_DBL;
                pnt_end = pnt_sta - norm_vec;
                plot3([pnt_sta(1),  pnt_end(1)], ...
                      [pnt_sta(2),  pnt_end(2)], ...
                      [pnt_sta(3),  pnt_end(3)], '-b', 'LineWidth',5);
            end
        end
        %==================================================================
        function str = get_label(OBJ)
                 str = OBJ.label_str; 
        end
        %==================================================================
        function abcd_row = get_abcd_list(OBJ)
                 abcd_row = [OBJ.n_normal_row, OBJ.d_val ]; 
        end
        %==================================================================
        function tf_are_para = AreParaPlanes(OBJ, PL2)
            validateattributes(PL2, {'ctm_plane'}, {'scalar'});
            
            tf_are_para = false;
            
            PL1 = OBJ;
            
            N1  = PL1.n_normal_row/norm(PL1.n_normal_row);
            N2  = PL2.n_normal_row/norm(PL2.n_normal_row);
            
            % CONCEPT: 
            %  a.) Let N1 and N2 be two UNIT vectors
            %  b.) we take their DOT product:
            %        N1.N2 = |N1| * |N2| * cos(theta)
            %              =    1 *    1 * cos(theta)
            %              = cos(theta)
            %  c.) if the vectors are parallel, 
            %     then theta == 0 or 180 deg
            %     then cos(theta) = 1 or -1
            %
            %   d.) so  abs(N1.N2) = 1 for parallel UNIT vectors
                      
            res             = dot(N1, N2);
            res             = abs(res);
            dist_from_UNITY = abs(1-res);
            dist_from_UNITY = simplify(dist_from_UNITY);
                      
            if( isequal(dist_from_UNITY, sym(0))  )
                    tf_are_para = true;
            else
                num_ans = double(dist_from_UNITY);
                if( num_ans < 1e-10 )
                    tf_are_para = true;
                end
            end
                  
        end
        %==================================================================
        function dist = distance(OBJ, some_thing)
          % dist = distance(PL1, point )
          % dist = distance(PL1, a_LINE )
          % dist = distance(PL1, a_PLANE )
          
          PL1 = OBJ;
          switch(class(some_thing))
            case 'ctm_point'
                 % REF: http://mathworld.wolfram.com/Point-PlaneDistance.html
                  pnt      = some_thing;
                  pnt_xyz  = pnt.get_coords_row();
                  abcd_row = PL1.get_abcd_list();
                  abc_row  = abcd_row(1:3);
                  d        = abcd_row(4);
                  dist     = (sum(abc_row .* pnt_xyz) + d) / norm(abc_row);
                  dist     = abs(dist);
                  
            case 'ctm_line'
                 % REF: http://ocw.mit.edu/courses/mathematics/18-02sc-multivariable-calculus-fall-2010/1.-vectors-and-matrices/part-c-parametric-equations-for-curves/session-16-intersection-of-a-line-and-a-plane/MIT18_02SC_we_9_comb.pdf      

                 a_LINE  = some_thing;
                 % does the line intersect the plane
                 [tmp_point, tf_does_intersect] = OBJ.intersection( a_LINE );
                 
                 if(tf_does_intersect)
                     dist = sym(NaN);
                     tmp_str = sprintf('***_ATTENTION_***:  your PLANE<%s> and LINE<%s> intersect !', ...
                                        PL1.get_label(), a_LINE.get_label() );
                     disp(tmp_str);
                 else
                     % Get one of the direction lines of the plane
                     V1_LINE = OBJ.dir_line_1;
                     dist    = distance(a_LINE, V1_LINE);
                 end
              case 'ctm_plane'
                  PL2  = some_thing;
                  % asking for the distance between 2 planes
                  % only makes sense if the planes are parallel
                  if( AreParaPlanes(PL1, PL2) )
                     % so get a point on PLANE2 and calc distance from there to
                     % PLANE1
                     dist = distance(PL1, PL2.pnt_on_plane );
                  else
                     dist = sym(NaN);
                     disp('***_ATTENTION_***:  your planes are NOT parallel !');
                     tmp_str = sprintf('***_ATTENTION_***: PLANE<%s> and PLANE<%s> are NOT parallel !', ...
                                        PL1.get_label(), PL2.get_label() );
                     disp(tmp_str);
                  end
          otherwise
                     error('ERROR: unsupported usage mode');
          end
        end
        %==================================================================
        function [ret_thing, tf_does_intersect] = intersection(OBJ, some_thing)
          % a_point = intersection(PL1, a_LINE )
          % a_line  = intersection(PL1, a_PLANE )
          %--------------------------------------------------------
          % [a_point,tf_does_intersect] = intersection(PL1, a_LINE )
          % [a_line,tf_does_intersect]  = intersection(PL1, a_PLANE )
          %--------------------------------------------------------
          
          PL1 = OBJ;
          
          switch(class(some_thing))
              case 'ctm_plane'
                  PL2 = some_thing;
                  if( AreParaPlanes(PL1, PL2) )
                      % so the 2 planes are parallel
                      tmp_pt = ctm_point('a_bad_point', NaN, NaN, NaN);
                      ret_thing = ctm_line('a_bad_LINE', {tmp_pt, [NaN,NaN,NaN]});
                      disp('***_ATTENTION_***:  your planes are parallel !');
                      tf_does_intersect = false;
                  else
                      % so the planes are NOT parallel
                      % REF: http://mathworld.wolfram.com/Plane-PlaneIntersection.html

                      n1 = PL1.n_normal_row / norm(PL1.n_normal_row);
                      p1 = PL1.d_val        / norm(PL1.n_normal_row);

                      n2 = PL2.n_normal_row / norm(PL2.n_normal_row); 
                      p2 = PL2.d_val        / norm(PL2.n_normal_row);

                      m = [ n1;
                            n2; ];

                      b = -[p1;
                            p2 ; ];

                      x0      = m\b;
                      dir_vec = null(m);

                      % scale the direction vector so that the smallest NON zero
                      % value is 1
                      tf_vec = [isequal(dir_vec(1), sym(0)), ...
                                isequal(dir_vec(2), sym(0)), ...
                                isequal(dir_vec(3), sym(0)) ];

                       non_z_vals = dir_vec(~tf_vec);
                       if(isempty(non_z_vals))        
                          scale_fact = sym(1);
                       else
                          scale_fact = min(abs(dir_vec)); 
                       end 
                       dir_vec = dir_vec/scale_fact;

                      dir_vec = double(dir_vec);

                      tmp_str = [PL1.label_str,'_INTSECT_',PL2.label_str];

                      pnt_x0 = ctm_point('X0', x0(1), x0(2), x0(3) );
                      ret_thing = ctm_line(tmp_str, {pnt_x0, [dir_vec(:)].'} ); 
                      
                      tf_does_intersect = true;
                  end
              case 'ctm_line'
                   L1    = some_thing;
                   L_p0  = double( L1.get_p0  );
                   L_dir = double( L1.get_dir );
                   L_geo_format = [L_p0(:)', L_dir(:)' ];
                  
                  P_geo_format = PL1.get_geo_row_from_createPlane();
                  
                  geo_point = intersectLinePlane(L_geo_format, P_geo_format);
                  
                  tmp_str = [PL1.label_str,'_INTSECT_',L1.get_label];
                  ret_thing = ctm_point('P1', geo_point(1), geo_point(2), geo_point(3) );
                  
                  if(  any(isnan(ret_thing.get_coords_row))  )
                      tf_does_intersect = false;
                  else
                      tf_does_intersect = true;
                  end
                  
              otherwise
                  error('ERROR: unsupported usage mode');
          end % switch
        end % intersection
        %==================================================================
    end
    
end
%_#########################################################################
%_ END of CLASSDEF
%_#########################################################################
function [n_row, d_val] =  LOC_parse_EQ_INP( INP_EQIV_EQ, INP_sym_list )
% example of expected INPUT:
%      INP_EQIV_EQ:   2*x + 0*y - 7*z==9
%      INP_sym_list:  [x,y,z]

% there should be EXACTLY 3 unique symbols in our list
assert(3==numel(INP_sym_list));

% now parse the equation
ch = children(INP_EQIV_EQ);
assert(2==numel(ch));

    if( 0==numel(symvar(ch(2))) && 0==numel(symvar(ch(1))) )
        % catch this fringe case early, eg:
        %   0*x + 0*y + 0*z == 3
        error('ERROR:  unsupported usage - you have ALL zero coefficients');
elseif( 0==numel(symvar(ch(2))) && numel(symvar(ch(1))) > 0 )
        side_const   = ch(2);
        side_xyz_exp = ch(1);

elseif( 0==numel(symvar(ch(1))) && numel(symvar(ch(2))) > 0 )
        side_const   = ch(1);
        side_xyz_exp = ch(2);        
    else
        error('ERROR: unsupported calling syntax !');
    end

% just make sure our XYZ side has 4 or less terms
assert(numel(children(side_xyz_exp)) <=4 )
% just make sure our constant side has only 1 term
assert(1==numel(children(side_const)) )

for kk=1:length(INP_sym_list)
    THE_SYM = INP_sym_list(kk);
    [C,T] = coeffs(side_xyz_exp, THE_SYM, 'All');
    assert( 2==numel(C) | 1==numel(C) )
    
    if(2==numel(C))
        n_row(kk) = C(1);
    else
        n_row(kk) = sym(0);
    end
end

% compute d
d_val = side_xyz_exp - sum(n_row .* INP_sym_list) - side_const;

d_val = simplify(d_val);
end
%==========================================================================
function  a_pnt = LOC_calc_point_on_plane(my_n_vec, my_d_val)
   a = my_n_vec(1);
   b = my_n_vec(2);
   c = my_n_vec(3);
   d = my_d_val;

   if( ~isequal(c, sym(0))  ) 
       % so c is NOT zero
       x = 1;
       y = 1;
       z = -1*(d + a*x + b*y)/c;
       a_pnt = ctm_point('pnt_on_plane',x,y,z);
   elseif( ~isequal(b, sym(0))  )
       % so b is NOT zero
       x = 1;
       z = 1;
       y = -1*(d + a*x + c*z)/b;
       a_pnt = ctm_point('pnt_on_plane',x,y,z);
   elseif( ~isequal(a, sym(0))  )
       % so a is NOT zero
       y = 1;
       z = 1;
       x = -1*(d + b*y + c*z)/a;
       a_pnt = ctm_point('pnt_on_plane',x,y,z);
   else
       error('ERROR: why is your normal vector [0,0,0]');
   end
end
%==========================================================================
function LOC_echo_p0_V1_v2(p0_dat, V1_dat, V2_dat)
           p0_str = sprintf('[%s, %s, %s]',   char(p0_dat(1)), ...
                                              char(p0_dat(2)), ...
                                              char(p0_dat(3)) );

           V1_str = sprintf('[%s, %s, %s]',   char(V1_dat(1)), ...
                                              char(V1_dat(2)), ...
                                              char(V1_dat(3)) );
                                          
           V2_str = sprintf('[%s, %s, %s]',   char(V2_dat(1)), ...
                                              char(V2_dat(2)), ...
                                              char(V2_dat(3)) );
                                          
           str_of_stuff = repmat('-',1,45);
               fprintf('%s', str_of_stuff);
               fprintf('\n POINT on the plane:')
                  fprintf('\n %s', p0_str);
                  fprintf('\n%s', str_of_stuff);
               fprintf('\n V1 direction vector in the plane:')
                  fprintf('\n %s', V1_str);
                  fprintf('\n%s', str_of_stuff);
               fprintf('\n V2 direction vector in the plane:')
                  fprintf('\n %s', V2_str);
                  fprintf('\n%s', str_of_stuff);
                  fprintf('\n ');
end

