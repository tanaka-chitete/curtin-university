classdef ctm_point
    %CTM_POINT Point class for Curtin Maths dept
    %----------------------------------------------------------------------
    % USAGE SYNTAX:
    %   pntA = ctm_point('P1', x_NUM, y_NUM, z_NUM )
    %----------------------------------------------------------------------
    % EXAMPLE USAGE:
    %   pntA = ctm_point('P1', 1, 2, 3 )
    %----------------------------------------------------------------------
    % DEMO example:
    %   bh_intro_to_POINTS_LINES_PLANES
    %----------------------------------------------------------------------
    properties (Access = protected)
        label_str   = '';
        form_str    = 'ctm_point';
        co_ords_row = sym([0,0,0]);
    end
    
    methods
        function OBJ = ctm_point(label_str, x, y, z)
            % P = ctm_point('P1', x_NUM, y_NUM, z_NUM )
            
            % do a little bit of argument checking
            validateattributes(label_str,{'char'},{'vector'});
            validateattributes(x, {'numeric', 'sym'}, {'scalar'}   );
            validateattributes(y, {'numeric', 'sym'}, {'scalar'}   );
            validateattributes(z, {'numeric', 'sym'}, {'scalar'}   );
            
            % OK: get on with the job
            OBJ.label_str   = label_str;
            OBJ.co_ords_row = sym(  [x,y,z]  );
        end
        %==================================================================
        function display(OBJ)
            
           coord_str = sprintf('%s, %s, %s ', char(OBJ.co_ords_row(1)), ...
                                              char(OBJ.co_ords_row(2)), ...
                                              char(OBJ.co_ords_row(3)) );
           coord_str(end) = '';
           coord_str = ['[',coord_str,']'];
            
           fprintf('\n label of object          %s', OBJ.label_str);
           fprintf('\n form of object           %s', OBJ.form_str);
           fprintf('\n coordinates of the point %s', coord_str);
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
        function the_dist = distance(OBJ, THING)
            % d = distance(p1, p2)
            % d = distance(p1, LINE)
            % d = distance(p1, PLANE)
            validateattributes(THING, {'ctm_point', 'ctm_line', 'ctm_plane'}, {'scalar'});

            p1 = OBJ;
            switch(class(THING))
                case 'ctm_point'  
                   p2       = THING; 
                   the_diff = OBJ.co_ords_row - p2.co_ords_row;
                   the_dist = norm(the_diff);
                case 'ctm_line'
                   % calculate the MINIMUM distance from the point to the line
                   % REF:  http://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html

                   L = THING;
                   x0 = p1.co_ords_row();
                   x1 = L.get_p0();
                   x2 = L.get_p0() + 1*L.get_dir();
                   
                   top_part = norm( cross( (x0-x1),(x0-x2) ) );
                   bot_part = norm( x2-x1 );
                   d        = top_part/bot_part;
                   the_dist = simplify(d);
                case 'ctm_plane'
                    a_PLANE  = THING;
                    the_dist = distance(a_PLANE, p1);
                otherwise
                    error('ERROR: unknown input type !');
            end
        end
        %==================================================================
        function the_cords = get_coords_row(OBJ)
                 the_cords = OBJ.co_ords_row;
        end
        %==================================================================
        function x = get_x(OBJ)
                 x = OBJ.co_ords_row(1);
        end
        %==================================================================
        function y = get_y(OBJ)
                 y = OBJ.co_ords_row(2);
        end
        %==================================================================
        function z = get_z(OBJ)
                 z = OBJ.co_ords_row(3);
        end
        %==================================================================
        function new_pt = minus(OBJ, P2)
                 P1 = OBJ;
                 new_pt = P1;
                 new_pt.label_str   = [P1.label_str, '-', P2.label_str ];
                 new_pt.co_ords_row = P1.co_ords_row - P2.co_ords_row;
        end
        %==================================================================
        function OBJ = set_label(OBJ, some_str)
                 OBJ.label_str = some_str;
        end
        %==================================================================
        function plot(OBJ, varargin)
            try
                xyz_dat  = double(OBJ.co_ords_row);
            catch
                disp('You do NOT have Numeric data !');
            end
            
            if(1==nargin)
               opts_CE_for_plot3  = {'-ro'};
            else
                opts_CE_for_plot3 = varargin;
            end
            
            % just use the existing PLOT3() function
            hgL = plot3(xyz_dat(1), ...
                        xyz_dat(2), ...
                        xyz_dat(3),  opts_CE_for_plot3{:});
              
            set(gca,'Box', 'on', 'BoxStyle', 'full');
            
            grid(gca,'on');
            
            xlabel(gca, 'X', 'FontWeight', 'Bold' )
            ylabel(gca, 'Y', 'FontWeight', 'Bold' )
            zlabel(gca, 'Z', 'FontWeight', 'Bold' )
        end
        %==================================================================        
        
    end
    
end % classdef

