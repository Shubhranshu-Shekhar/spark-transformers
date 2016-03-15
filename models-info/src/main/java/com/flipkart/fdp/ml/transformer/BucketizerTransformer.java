package com.flipkart.fdp.ml.transformer;

import com.flipkart.fdp.ml.modelinfo.BucketizerModelInfo;

import java.util.Arrays;

/**
 * Transforms input/ predicts for a Bucketizer model representation
 * captured by  {@link com.flipkart.fdp.ml.modelinfo.BucketizerModelInfo}.
 */
public class BucketizerTransformer implements Transformer {

    private final BucketizerModelInfo modelInfo;

    public BucketizerTransformer(final BucketizerModelInfo modelInfo) {
        this.modelInfo = modelInfo;
    }

    public double predict(final double input) {
        if( modelInfo.getSplits().length <=0) {
            throw new RuntimeException("BucketizerTransformer : splits have incorrect length : " + modelInfo.getSplits().length);
        }

        final double last = modelInfo.getSplits()[modelInfo.getSplits().length-1];
        if(input == last) {
            return modelInfo.getSplits().length - 2;
        }

        int idx = Arrays.binarySearch(modelInfo.getSplits(), input);
        if (idx >= 0) {
            return idx;
        } else {
            int insertPos = -idx - 1;
            if (insertPos == 0 || insertPos == modelInfo.getSplits().length) {
                throw new RuntimeException("BucketizerTransformer : Feature value : " + input + " out of bounds : (" + modelInfo.getSplits()[0] + "," + last + ")");
            } else {
                return insertPos - 1;
            }
        }
    }

    @Override
    public Object[] transform(Object[] input) {
        if(input.length > 1) {
            throw new IllegalArgumentException("BucketizerTransformer does not support arrays of length more than 1");
        }
        return new Double[] {predict((Double)input[0])};
    }
}
