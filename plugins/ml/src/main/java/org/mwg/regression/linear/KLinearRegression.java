package org.mwg.regression.linear;

import org.mwg.Node;
import org.mwg.classifier.common.KSlidingWindowManagingNode;

/**
 * Created by andre on 4/26/2016.
 */
public interface KLinearRegression extends KSlidingWindowManagingNode {

    /**
     * @return Regression coefficients. Intercept is in place of response index.
     */
    double[] getCoefficients();
}