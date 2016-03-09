package com.flipkart.fdp.ml.transformer;

import com.flipkart.fdp.ml.modelinfo.StringIndexerModelInfo;

/**
 * Transforms input/ predicts for a String Indexer model representation
 * captured by  {@link com.flipkart.fdp.ml.modelinfo.StringIndexerModelInfo}.
 */
public class StringIndexerTransformer implements Transformer {

    private final StringIndexerModelInfo modelInfo;

    public StringIndexerTransformer(final StringIndexerModelInfo modelInfo) {
        this.modelInfo = modelInfo;
    }

    public double transform(final String s) {
        final Double index = modelInfo.getLabelToIndex().get(s);
        if (null == index) {
            throw new RuntimeException("Unseen label :" + s);
        }
        return index;
    }

    //TODO: what finalise on the Transformer interface
    @Override
    public double transform(double[] input) {
        throw new UnsupportedOperationException("transform on double values is not supported by StringIndexer");
    }
}
