import org.json.*;

public class InteractiveGrapher {
    /*
     * NAME: makeAssetGraph
     * IMPORT(S): exchangeInfo (JSONObject)
     * EXPORT(S): assetGraph (DSAGraph)
     * PURPOSE: Given trade pairs from exchange info, make asset graphs
     * CREATION: 07/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static DSAGraph makeAssetGraph(JSONObject exchangeInfo) {
        JSONArray tradePairs = exchangeInfo.getJSONArray("symbols");
        boolean isDirected = true;
        DSAGraph assetGraph = new DSAGraph(isDirected);

        // Adds trade pairs to graph
        for (int i = 0; i < tradePairs.length(); i++) {
            JSONObject currentTradePair = tradePairs.getJSONObject(i);
            String currentBaseAsset = currentTradePair.getString("baseAsset");
            String currentQuoteAsset = 
                currentTradePair.getString("quoteAsset");

            if (!assetGraph.hasVertex(currentBaseAsset)) {
                assetGraph.addVertex(currentBaseAsset);
            }
            
            if (!assetGraph.hasVertex(currentQuoteAsset)) {
                assetGraph.addVertex(currentQuoteAsset);
            }

            if (!assetGraph.areNeighbours(currentBaseAsset, 
                currentQuoteAsset)) {
                assetGraph.addEdge(currentBaseAsset, currentQuoteAsset);
            }
        }

        return assetGraph;
    }
}
