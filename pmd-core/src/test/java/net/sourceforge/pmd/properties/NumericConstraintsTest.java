/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

class NumericConstraintsTest {

    @Test
    void testInRangeInteger() {
        PropertyConstraint<Integer> constraint = NumericConstraints.inRange(1, 10);
        assertNull(errorAsString(constraint, 1));
        assertNull(errorAsString(constraint, 5));
        assertNull(errorAsString(constraint, 10));
        assertNotNull(errorAsString(constraint, 0));
        assertEquals("'-1' should be between 1 and 10", errorAsString(constraint, -1));
        assertNotNull(errorAsString(constraint, 11));
        assertNotNull(errorAsString(constraint, 100));
    }

    private @Nullable <T> String errorAsString(PropertyConstraint<T> constraint, T value) {
        try {
            constraint.validate(value);
            return null;
        } catch (ConstraintViolatedException e) {
            return e.getMessage();
        }
    }

    @Test
    void testInRangeDouble() {
        PropertyConstraint<Double> constraint = NumericConstraints.inRange(1.0, 10.0);
        assertNull(errorAsString(constraint, 1.0));
        assertNull(errorAsString(constraint, 5.5));
        assertNull(errorAsString(constraint, 10.0));
        assertNotNull(errorAsString(constraint, 0.0));
        assertNotNull(errorAsString(constraint, -1.0));
        assertNotNull(errorAsString(constraint, 11.1));
        assertNotNull(errorAsString(constraint, 100.0));
    }

    @Test
    void testPositive() {
        PropertyConstraint<Number> constraint = NumericConstraints.positive();
        assertNull(errorAsString(constraint, 1));
        assertNull(errorAsString(constraint, 1.5f));
        assertNull(errorAsString(constraint, 1.5d));
        assertNull(errorAsString(constraint, 100));
        assertNotNull(errorAsString(constraint, 0));
        assertEquals("'0.1' should be positive", errorAsString(constraint, 0.1f));
        assertNotNull(errorAsString(constraint, 0.9d));
        assertNotNull(errorAsString(constraint, -1));
        assertNotNull(errorAsString(constraint, -100));
        assertNotNull(errorAsString(constraint, -0.1f));
        assertNotNull(errorAsString(constraint, -0.1d));
    }
}
