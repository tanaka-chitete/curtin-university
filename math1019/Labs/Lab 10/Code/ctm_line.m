classdef ctm_line
    %CTM_LINE Line class for Curtin Maths dept
    %
    %   Line is represented in a parametric form : [x0 y0 z0 dx dy dz]
    %       x = x0 + t*dx
    %       y = y0 + t*dy;
    %       z = z0 + t*dz;
    %----------------------------------------------------------------------
    % USAGE SYNTAX:
    %   L = ctm_line('L1', {pA, pB} )
    %   L = ctm_line('L1', {pt, dir_vec_NUM} )
    %   L = ctm_line('L1', [1+t, b-2*t, 1+c*t], t )
    %----------------------------------------------------------------------
    % EXAMPLE USAGE - syntax #1:
    %   pntA = ctm_point('P1', 1, 2, 3 )
    %   pntB = ctm_point('PB', 4, 5, 6 )
    %   L    = ctm_line('L1', {pntA, pntB} )
    %----------------------------------------------------------------------
    % EXAMPLE USAGE - syntax #2:
    %   pntA      = ctm_point('P1', 1, 2, 3 )
    %   a_dir_vec = [7,8,9]
    %   L         = ctm_line('L1', {pntA, a_dir_vec} )
    %----------------------------------------------------------------------
    % EXAMPLE USAGE - syntax #3:
    %   syms t
    %   L = ctm_line('L1', [1+t, b-2*t, 1+c*t], t )
    %----------------------------------------------------------------------
    % DEMO example:
    %   bh_intro_to_POINTS_LINES_PLANES
    %----------------------------------------------------------------------
    properties (Access = protected)
        label_str       = '';
        form_str        = 'ctm_line';
        sym_eqn_of_line = [];
        the_sym_var     = sym('t');
        p0_row          = sym([0,0,0]);
        dir_row         = sym([0,0,0]);
    end
    
    methods
        function OBJ = ctm_line(label_str, varargin)
            % L = ctm_line('L1', {pA, pB} )
            % L = ctm_line('L1', {pt, dir_vec_NUM} )
            % L = ctm_line('L1', [1+t, b-2*t, 1+c*t], t )
            
            % some initial argument checking
            validateattributes(label_str,{'char'},{'vector'});
            
            % OK: get on with the job
            OBJ.label_str   = label_str;
            
            if( 2 == nargin )
                some_CE = varargin{1};
                 if( isa(some_CE{1},'ctm_point') &&  ...
                     isa(some_CE{2},'ctm_point') )
                     % L = ctm_line('L1', {pA, pB} )
                     
                     p1 = some_CE{1};
                     p2 = some_CE{2};
                     
                     OBJ.p0_row  = p1.get_coords_row;
                     OBJ.dir_row = p2.get_coords_row - p1.get_coords_row; 
                     
                     T = OBJ.the_sym_var;
                     OBJ.sym_eqn_of_line = [OBJ.p0_row(1) + T*OBJ.dir_row(1), ...
                                            OBJ.p0_row(2) + T*OBJ.dir_row(2), ...
                                            OBJ.p0_row(3) + T*OBJ.dir_row(3) ];
                 elseif( isa(some_CE{1}, 'ctm_point') &&  ...
                         isa(some_CE{2}, 'numeric')   &&  ...
                         3==numel(some_CE{2})  )
                     % L = ctm_line('L1', {pt, dir_vec} )
                     
                     p1          = some_CE{1};
                     dir_vec     = some_CE{2};
                     OBJ.p0_row  = p1.get_coords_row;
                     OBJ.dir_row = [dir_vec(:)].'; 
                     
                     T = OBJ.the_sym_var;
                     OBJ.sym_eqn_of_line = [OBJ.p0_row(1) + T*OBJ.dir_row(1), ...
                                            OBJ.p0_row(2) + T*OBJ.dir_row(2), ...
                                            OBJ.p0_row(3) + T*OBJ.dir_row(3) ];
                     
                 else
                     error('ERROR:  Unknown calling syntax');    
                 end
            elseif( 3==nargin )
                % L = ctm_line('L1', [1+7*t, 9-2*t, 1+8*t], t )
                some_CE = varargin;
                validateattributes(some_CE{1},{'sym'},{'vector', 'numel',3});
                validateattributes(some_CE{2},{'sym'},{'scalar'});
                
                OBJ.the_sym_var     = some_CE{2}; 
                
                % I will only permit 1 SYMBOLIC variable in the parametric
                % equation
                tmp_express_list = some_CE{1}; 
                assert(1==length(symvar(tmp_express_list)) )
                
                % parse the expression amd pull out the p0 and dir vectors
                [p0_row, dir_row] =  LOC_parse_param_express_list( some_CE{1}, some_CE{2} );
                
                 OBJ.p0_row          = p0_row;
                 OBJ.dir_row         = dir_row;
                 T                   = OBJ.the_sym_var;

                 OBJ.sym_eqn_of_line = [OBJ.p0_row(1) + T*OBJ.dir_row(1), ...
                                        OBJ.p0_row(2) + T*OBJ.dir_row(2), ...
                                        OBJ.p0_row(3) + T*OBJ.dir_row(3) ];
            else
                error('ERROR: UNknown usage mode !');
            end
            
            % just make sure we have SYM objects
            OBJ.p0_row  = sym(OBJ.p0_row); 
            OBJ.dir_row = sym(OBJ.dir_row);
        end
        %==================================================================
        function p0_row = get_p0(OBJ)
                 p0_row = OBJ.p0_row;
        end
        %==================================================================
        function dir_row = get_dir(OBJ)
                 dir_row = OBJ.dir_row;
        end
        %==================================================================
        function x_eqn = get_x_eqn(OBJ)
                 x_eqn = OBJ.sym_eqn_of_line(1);
        end
        %==================================================================
        function y_eqn = get_y_eqn(OBJ)
                 y_eqn = OBJ.sym_eqn_of_line(2);
        end
        %==================================================================
        function z_eqn = get_z_eqn(OBJ)
                 z_eqn = OBJ.sym_eqn_of_line(3);
        end
        %==================================================================
        function str = get_label(OBJ)
                 str = OBJ.label_str; 
        end
        %==================================================================
        function display(OBJ)
           eqn_str = '';
           ax_list = {'x','y','z'};
           for kk=1:3
               tmp_str = sprintf('%s= %s + %s*%s, ', ...
                                 ax_list{kk}, ...
                                 char(OBJ.p0_row(kk)), ...
                                 char(OBJ.dir_row(kk)), ...
                                 char(OBJ.the_sym_var) );
               eqn_str = [eqn_str, tmp_str];              
           end
           eqn_str(end-1:end) = ''; 
           eqn_str = ['[',eqn_str,']']; 
           
           fprintf('\n label of object       %s', OBJ.label_str);
           fprintf('\n form of object        %s', OBJ.form_str);
           fprintf('\n equation of the line  %s', eqn_str);
           fprintf('\n')
        end
        %==================================================================
         function detail(OBJ)
                  display(OBJ)
         end
        %==================================================================
        function form(OBJ)
           fprintf('\n %s',OBJ.form_str);
           fprintf('\n')            
        end
        %==================================================================
        function tf_is_para = AreParaLines(OBJ,L2)
            validateattributes(L2, {'ctm_line'}, {'scalar'}   );
            L1 = OBJ;
            
            tf_is_para = false;
            
            % CONCEPT: 
            %  a.) Let u1 and u2 be two UNIT vectors
            %  b.) we take their DOT product:
            %        u1.u2 = |u1| * |u2| * cos(theta)
            %              =    1 *    1 * cos(theta)
            %              = cos(theta)
            %  c.) if the lines are parallel, 
            %     then theta == 0 or 180 deg
            %     then cos(theta) = 1 or -1
            %
            %   d.) so  abs(u1.u2) = 1 for paralel UNIT vectors
            
            u1 = L1.dir_row ./ norm(L1.dir_row);
            u2 = L2.dir_row ./ norm(L2.dir_row);
            
            res             = dot(u1, u2);
            res             = abs(res);
            dist_from_UNITY = abs(1-res);
            dist_from_UNITY = simplify(dist_from_UNITY);
            
            
            if( isequal(dist_from_UNITY, sym(0))  )
                    tf_is_para = true;
            else
                num_ans = double(dist_from_UNITY);
                if( num_ans < 1e-10 )
                    tf_is_para = true;
                end
            end
            
        end
        %==================================================================
       function tf_is_cop = AreCoplanarLines(OBJ, L2)
            validateattributes(L2, {'ctm_line'}, {'scalar'}   );
            L1 = OBJ;
            
            % REF:  http://mathworld.wolfram.com/Coplanar.html
            p1_row =            double(L1.p0_row); 
            p2_row = p1_row + 1*double(L1.dir_row);
            
            p3_row =            double(L2.p0_row); 
            p4_row = p3_row + 1*double(L2.dir_row);

            A = [ p1_row, 1;
                  p2_row, 1;
                  p3_row, 1;
                  p4_row, 1;
                  ];
              
            the_res =  det(A);
            
            if( abs(the_res) < 1e-10 )
                tf_is_cop = true;
            else
                tf_is_cop = false;
            end            
       end
       %==================================================================
        function tf_is_skew = AreSkewLines(OBJ, L2)
            % http://mathworld.wolfram.com/SkewLines.html
            validateattributes(L2, {'ctm_line'}, {'scalar'}   );
            
            L1         = OBJ;
            tf_is_cop  = AreCoplanarLines(L1, L2);
            tf_is_skew = ~tf_is_cop;            
        end
        %==================================================================
        function pINT = intersection(OBJ, L2)
            % pINT = intersection(L2, L2)
            
            validateattributes(L2, {'ctm_line'}, {'scalar'}   );
            L1 = OBJ;
            tf_are_skew = AreSkewLines(L1, L2);
            tf_are_para = AreParaLines(L1, L2);
            
            new_label_str = ['INTERSECTION_OF: <',L1.label_str,'> and <',L2.label_str,'>' ];
            if(tf_are_skew)
                 pINT = ctm_point(new_label_str, NaN, NaN, NaN);
                 disp('********** Lines are SKEW *********');
            elseif(tf_are_para) 
                 pINT = ctm_point(new_label_str, NaN, NaN, NaN);
                 disp('********* Lines are PARALLEL ********');
            else
                 [x,y,z] = LOC_intersection(L1, L2);
                 pINT    = ctm_point(new_label_str, x, y, z);
            end
        end
        %==================================================================
        function the_dist = distance(OBJ, THING)
            % d = distance(L1, pnt1)
            % d = distance(L1, L2)
            % d = distance(L1, PL1)
            validateattributes(THING, {'ctm_point', 'ctm_line', 'ctm_plane'}, {'scalar'});

            L1 = OBJ;
            switch(class(THING))
                case 'ctm_point' 
                  P1 = THING;
                  the_dist = distance(P1, L1);
                case 'ctm_line'
                   L2 = THING;
                   if( AreSkewLines(L1, L2) )
                       % REF: http://mathworld.wolfram.com/Line-LineDistance.html
                    
                       x1   = L1.get_p0();
                       x2m1 = L1.get_dir();
                       x3   = L2.get_p0();
                       x4m3 = L2.get_dir();
                       
                       a    = x2m1;
                       b    = x4m3;
                       c    = x3 - x1;
                       
                       tmp_top = dot(c, cross(a,b) );
                       tmp_bot =        cross(a,b)  ;
                       
                       the_dist = abs(tmp_top) / norm(tmp_bot);
                       the_dist = simplify(the_dist);
                      
                   else
                       the_dist = sym(NaN);
                       disp('***_ATTENTION_***:  the lines were NOT SKEW');
                   end
                case 'ctm_plane'
                    PL1      = THING;
                    the_dist = distance(PL1, L1);
                otherwise
                    error('ERROR: unknown input type !');
            end
        end
        %==================================================================
        function plot(OBJ, varargin)
            try
                p0_row  = double(OBJ.p0_row);
                dir_row = double(OBJ.dir_row);
            catch
                disp('You do NOT have Numeric data !');
            end
            
            if(1==nargin)
               opts_CE_for_fplot3  = {'-mo', 'LineWidth', 2};
            else
                opts_CE_for_fplot3 = varargin;
            end
            
            fplot3(OBJ.sym_eqn_of_line(1), ...
                   OBJ.sym_eqn_of_line(2), ...
                   OBJ.sym_eqn_of_line(3),  opts_CE_for_fplot3{:});
              
            set(gca,'Box', 'on', 'BoxStyle', 'full');
            
            xlabel(gca, 'X', 'FontWeight', 'Bold' )
            ylabel(gca, 'Y', 'FontWeight', 'Bold' )
            zlabel(gca, 'Z', 'FontWeight', 'Bold' )
        end
        %==================================================================
    end
    
end % classdef
%_#########################################################################
%_ END of CLASSDEF
%_#########################################################################
function [p0_row, dir_row] =  LOC_parse_param_express_list(exp_list, the_sym_var)

    %     v = [2+4*A, 7-5*A, 0]
    % [C,T] = coeffs(v(1), A, 'All')
    % so we'd get: C = [ 4, 2]
    %              T = [ A, 1]
    %  
    % [C,T] = coeffs(v(3), A, 'All')
    % so we'd get: C = Empty sym: 1-by-0
    %              T = Empty sym: 1-by-0

    for kk=1:length(exp_list)
        [C,T] = coeffs(exp_list(kk), the_sym_var, 'All');

        switch( length(C) )
            case {0}
                   p0_row(kk) = 0;
                   dir_row(kk) = 0;
            case {2}
                   p0_row(kk)  = C(2);
                   dir_row(kk) = C(1);
            otherwise
                  error('ERROR:  your expression should be linear');    
        end
    end % kk

end
%==========================================================================
function [x,y,z] = LOC_intersection(L1, L2)

    L1_symvar = symvar(L1.sym_eqn_of_line);
    L2_symvar = symvar(L2.sym_eqn_of_line);

    assert(1==length(L1_symvar) );
    assert(1==length(L2_symvar) );

    XYZ1_eqn = [L1.get_x_eqn; L1.get_y_eqn; L1.get_z_eqn];
    XYZ1_eqn = subs(XYZ1_eqn, L1_symvar, 't' );

    XYZ2_eqn = [L2.get_x_eqn; L2.get_y_eqn; L2.get_z_eqn];
    XYZ2_eqn = subs(XYZ2_eqn, L2_symvar, 's' );

    the_sol = solve(XYZ1_eqn(1:2)==XYZ2_eqn(1:2));

    x = subs(XYZ1_eqn(1), ['t'], [the_sol.t]);
    y = subs(XYZ1_eqn(2), ['t'], [the_sol.t]);
    z = subs(XYZ1_eqn(3), ['t'], [the_sol.t]);

    x = double(x);
    y = double(y);
    z = double(z);
end

