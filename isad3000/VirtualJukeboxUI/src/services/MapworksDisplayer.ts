import { useUser } from "../stores/user";
import { useSession, type sessionType } from "../stores/session";

const MapworksDisplayer = {
  display(sessions: sessionType[]) {
    const userStore = useUser();
    const sessionStore = useSession();
    const { Studio } = window as any;
    const { id = "AXhI8CSUAAA2ac12AAAA" } = Studio._.urlParamObj();

    const config = {
      map: id,
      mapworksPath: "https://amristar.mapworks.io",
      access: Studio.core.Map.Access.ANONYMOUS,
      navigationControl: false,
      scaleControl: true,
      toolbarControl: false,
      zoomControl: false,
      mapworksLoginProvider: {
        client_id: "307u62uqvt7iid9ldii08qpguf",
        popup_redirect_uri: import.meta.env.VITE_APP_URL + "/openId.html",
        silent_redirect_uri: import.meta.env.VITE_APP_URL + "/openId.html",
        anonymousUser: "noreply@public-anonymous.mapworks.io",
      },
    };

    /* Disable the search control */
    Studio.app.App._initSearch = function () {};

    const map = ((window as any).map = Studio.init("#embed1", config).once(
      "ready",
      function () {
        const layerConfig = {
          attributes: {
            maxScale: map.getMaxScale(),
            minScale: map.getMinScale(),
          },
          title: "Markup Layer",
          visible: true,
          labelled: true,
          fields: {
            title: {
              name: "title",
              title: "Name",
              type: "string",
            },
            description: {
              name: "description",
              title: "Description",
              type: "string",
            },
            range: {
              name: "range",
              title: "Range",
              type: "string",
            },
            distance: {
              name: "distance",
              title: "Distance",
              type: "string",
            },
          },
          layerFields: [
            {
              name: "name",
              type: "string",
            },
            {
              name: "description",
              type: "string",
            },
            {
              name: "range",
              type: "string",
            },
            {
              name: "distance",
              type: "string",
            },
          ],
          styles: {
            "#": {
              default: {
                pointFill: "#FF0081",
                lineFill: "#FF0081",
                lineWidth: [1],
                polygonFill: "#001822",
                polygonLineFill: "#001822",
                polygonLineWidth: 1,
                polygonOpacity: 0.3,
                pointWidth: 18,
                labelFont: "Manrope",
                labelSize: [12],
                labelTemplate: null,
                hoverTemplate: "|title|",
                labelPriority: 6,
                labelForce: true,
                expressions: {
                  pointFill:
                    'case(properties.title=="You", "#FF0081", true, "#574ae2")',
                  pointIcon:
                    'case(properties.title=="You", "' +
                    import.meta.env.VITE_APP_URL +
                    "/src/assets/user-pin-small.png" +
                    '", true, "' +
                    import.meta.env.VITE_APP_URL +
                    "/src/assets/session-pin-large.png" +
                    '")',
                },
              },
              active: {
                pointFill: "#FFFFFF",
                lineFill: "#FFFFFF",
                polygonFill: "#FFFFFF",
                polygonOpacity: 0.3,
              },
              selected: {
                pointFill: "yellow",
                lineFill: "yellow",
                polygonFill: "yellow",
                polygonOpacity: 0.3,
              },
            },
          },
          source: {
            adhoc: {
              features: {},
              fields: [
                {
                  name: "title",
                  type: 311,
                },
                {
                  name: "description",
                  type: 311,
                },
                {
                  name: "range",
                  type: 311,
                },
                {
                  name: "distance",
                  type: 311,
                },
              ],
            },
          },
        };
        const layer = new Studio.core.entity.TreeVectorLayerEntity(
          layerConfig,
          {
            map: map,
          }
        );

        map.getTree().add(layer);

        /* Disable mapworks popups from showing */
        map.getControl("tooltip").options.layerExceptions.push(layer.getId());

        /* Add user location to the map */
        const userLongitude = userStore.longitude;
        const userLatitude = userStore.latitude;
        const userPoint = map.createPoint(userLongitude, userLatitude, layer);
        userPoint.set({ id: "user-location" });
        userPoint.setFields({
          Name: "You",
          Description: "This is where you are right now",
        });

        for (let i = 0; i < sessions.length; i++) {
          const name = sessions[i].session_name;
          const description = sessions[i].session_description;
          const longitude =
            sessions[i].session_location
              .coordinates[0]; /* Real world X coordinate in map's coordinate system. */
          const latitude =
            sessions[i].session_location
              .coordinates[1]; /* Real world Y coordinate in map's coordinate system. */
          const session_id = sessions[i].id;
          const range_number = sessions[i].connection_range;
          const range_string =
            sessions[i].connection_range > 400
              ? "Unlimited"
              : sessions[i].connection_range + "m";
          const formatter = Intl.NumberFormat("en-US");
          const distance =
            formatter.format(Math.round(sessions[i].distance_to)) + "m";

          /* If the range is not unlimited */
          if (range_number <= 400) {
            /* Add a ring denoting the range for the session
            0.00001 is 1m for mapworks */
            const circle = map.createCircle(
              longitude,
              latitude,
              0.00001 * range_number,
              layer
            );

            circle.set({ id: "range" });
            circle.setFields({
              Name: "Range",
            });
          }

          /* Add a point for the session itself */
          const point = map.createPoint(longitude, latitude, layer);

          point.set({ id: session_id });
          point.setFields({
            Name: name,
            Description: description,
            Range: range_string,
            Distance: distance,
          });
        }

        map.listenTo(map, "feature:mouseclick", (mouseClickEvent: any) => {
          const feature = mouseClickEvent.getFeature();
          if (
            feature &&
            feature.attributes.layer.cid === layer.cid &&
            feature.getId() != "user-location" &&
            feature.getId() != "range"
          ) {
            /* Retrieve session id, name and description; place into store */
            sessionStore.session_id = feature.getId();

            const fields = feature.getFields(true);

            sessionStore.session_name = fields["title"];
            sessionStore.session_description = fields["description"];
            sessionStore.connection_range = fields["range"];
            sessionStore.distance = fields["distance"];

            /* Load confirm join session modal */
            const confirmJoinSessionModal = document.getElementById(
              "joinselectedsession"
            );
            if (confirmJoinSessionModal) {
              confirmJoinSessionModal.style.display = "block";
            }
          }
        });

        layer.redraw();
        /* Zoom into user location */
        map.animateTo({
          x: userLongitude,
          y: userLatitude,
          targetScale: 500 * 10,
        });
      }
    ));
  },
};

export default MapworksDisplayer;
