/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd;

import java.util.Comparator;
import java.util.Map;

import net.sourceforge.pmd.lang.document.FileLocation;
import net.sourceforge.pmd.util.DataMap.DataKey;

/**
 * A RuleViolation is created by a Rule when it identifies a violation of the
 * Rule constraints. RuleViolations are simple data holders that are collected
 * into a {@link Report}.
 *
 * <p>Since PMD 6.21.0, implementations of this interface are considered internal
 * API and hence deprecated. Clients should exclusively use this interface.
 *
 * @see Rule
 */
public interface RuleViolation {
    // todo move to package reporting

    /**
     * A comparator for rule violations. This compares all exposed attributes
     * of a violation, filename first. The remaining parameters are compared
     * in an unspecified order.
     */
    Comparator<RuleViolation> DEFAULT_COMPARATOR =
        Comparator.comparing(RuleViolation::getFilename)
                  .thenComparingInt(RuleViolation::getBeginLine)
                  .thenComparingInt(RuleViolation::getBeginColumn)
                  .thenComparing(RuleViolation::getDescription, Comparator.nullsLast(Comparator.naturalOrder()))
                  .thenComparingInt(RuleViolation::getEndLine)
                  .thenComparingInt(RuleViolation::getEndColumn)
                  .thenComparing(rv -> rv.getRule().getName());


    /**
     * Key in {@link #getAdditionalInfo()} for the name of the class in
     * which the violation was identified.
     */
    String CLASS_NAME = "className";
    /**
     * Key in {@link #getAdditionalInfo()} for the name of the variable
     * related to the violation.
     */
    String VARIABLE_NAME = "variableName";
    /**
     * Key in {@link #getAdditionalInfo()} for the name of the method in
     * which the violation was identified.
     */
    String METHOD_NAME = "methodName";
    /**
     * Key in {@link #getAdditionalInfo()} for the name of the package in
     * which the violation was identified.
     */
    String PACKAGE_NAME = "packageName";

    /**
     * Get the Rule which identified this violation.
     *
     * @return The identifying Rule.
     */
    Rule getRule();

    /**
     * Get the description of this violation.
     *
     * @return The description.
     */
    String getDescription();


    /**
     * Returns the location where the violation should be reported.
     */
    FileLocation getLocation();

    /**
     * Get the source file name in which this violation was identified.
     *
     * @return The source file name.
     */
    default String getFilename() {
        return getLocation().getFileName();
    }

    /**
     * Get the begin line number in the source file in which this violation was
     * identified.
     *
     * @return Begin line number.
     */
    default int getBeginLine() {
        return getLocation().getStartPos().getLine();
    }

    /**
     * Get the column number of the begin line in the source file in which this
     * violation was identified.
     *
     * @return Begin column number.
     */
    default int getBeginColumn() {
        return getLocation().getStartPos().getColumn();
    }

    /**
     * Get the end line number in the source file in which this violation was
     * identified.
     *
     * @return End line number.
     */
    default int getEndLine() {
        return getLocation().getEndPos().getLine();
    }

    /**
     * Get the column number of the end line in the source file in which this
     * violation was identified.
     *
     * @return End column number.
     */
    default int getEndColumn() {
        return getLocation().getEndPos().getColumn();
    }


    Map<String, String> getAdditionalInfo();


    /**
     * Get the package name of the Class in which this violation was identified.
     *
     * @return The package name.
     */
    @Deprecated
    default String getPackageName() {
        return getAdditionalInfo().get(PACKAGE_NAME);
    }

    /**
     * Get the name of the Class in which this violation was identified.
     *
     * @return The Class name.
     */
    @Deprecated
    default String getClassName() {
        return getAdditionalInfo().get(CLASS_NAME);
    }

    /**
     * Get the method name in which this violation was identified.
     *
     * @return The method name.
     */
    @Deprecated
    default String getMethodName() {
        return getAdditionalInfo().get(METHOD_NAME);
    }

    /**
     * Get the variable name on which this violation was identified.
     *
     * @return The variable name.
     */
    @Deprecated
    default String getVariableName() {
        return getAdditionalInfo().get(VARIABLE_NAME);
    }


    final class RuleViolationDataKey implements DataKey<RuleViolationDataKey, String> {

        private final String name;

        public RuleViolationDataKey(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
