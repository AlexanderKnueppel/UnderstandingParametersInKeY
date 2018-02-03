/*
 * Copyright (c) 1994, 2006, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.lang;


/**
 * The class {@code Math} contains methods for performing basic
 * numeric operations such as the elementary exponential, logarithm,
 * square root, and trigonometric functions.
 *
 * <p>Unlike some of the numeric methods of class
 * {@code StrictMath}, all implementations of the equivalent
 * functions of class {@code Math} are not defined to return the
 * bit-for-bit same results.  This relaxation permits
 * better-performing implementations where strict reproducibility is
 * not required.
 *
 * <p>By default many of the {@code Math} methods simply call
 * the equivalent method in {@code StrictMath} for their
 * implementation.  Code generators are encouraged to use
 * platform-specific native libraries or microprocessor instructions,
 * where available, to provide higher-performance implementations of
 * {@code Math} methods.  Such higher-performance
 * implementations still must conform to the specification for
 * {@code Math}.
 *
 * <p>The quality of implementation specifications concern two
 * properties, accuracy of the returned result and monotonicity of the
 * method.  Accuracy of the floating-point {@code Math} methods
 * is measured in terms of <i>ulps</i>, units in the last place.  For
 * a given floating-point format, an ulp of a specific real number
 * value is the distance between the two floating-point values
 * bracketing that numerical value.  When discussing the accuracy of a
 * method as a whole rather than at a specific argument, the number of
 * ulps cited is for the worst-case error at any argument.  If a
 * method always has an error less than 0.5 ulps, the method always
 * returns the floating-point number nearest the exact result; such a
 * method is <i>correctly rounded</i>.  A correctly rounded method is
 * generally the best a floating-point approximation can be; however,
 * it is impractical for many floating-point methods to be correctly
 * rounded.  Instead, for the {@code Math} class, a larger error
 * bound of 1 or 2 ulps is allowed for certain methods.  Informally,
 * with a 1 ulp error bound, when the exact result is a representable
 * number, the exact result should be returned as the computed result;
 * otherwise, either of the two floating-point values which bracket
 * the exact result may be returned.  For exact results large in
 * magnitude, one of the endpoints of the bracket may be infinite.
 * Besides accuracy at individual arguments, maintaining proper
 * relations between the method at different arguments is also
 * important.  Therefore, most methods with more than 0.5 ulp errors
 * are required to be <i>semi-monotonic</i>: whenever the mathematical
 * function is non-decreasing, so is the floating-point approximation,
 * likewise, whenever the mathematical function is non-increasing, so
 * is the floating-point approximation.  Not all approximations that
 * have 1 ulp accuracy will automatically meet the monotonicity
 * requirements.
 *
 * @author  unascribed
 * @author  Joseph D. Darcy
 * @since   JDK1.0
 */

public final class Math {


    /**
     * Returns the absolute value of an {@code int} value.
     * If the argument is not negative, the argument is returned.
     * If the argument is negative, the negation of the argument is returned.
     *
     * <p>Note that if the argument is equal to the value of
     * {@link Integer#MIN_VALUE}, the most negative representable
     * {@code int} value, the result is that same value, which is
     * negative.
     *
     * @param   a   the argument whose absolute value is to be determined
     * @return  the absolute value of the argument.
     */
	/*@ public normal_behavior
	  @ ensures (a <  0) ==> (\result == -a);
	  @ ensures (a >= 0) ==> (\result == a);
	  @ ensures (a == Integer.MIN_VALUE) ==> (\result == Integer.MIN_VALUE);
	  @*/
    public static /*@pure@*/ int abs(int a) {
        return (a < 0) ? -a : a;
    }


    /**
     * Returns the greater of two {@code int} values. That is, the
     * result is the argument closer to the value of
     * {@link Integer#MAX_VALUE}. If the arguments have the same value,
     * the result is that same value.
     *
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the larger of {@code a} and {@code b}.
     */
	/*@ public normal_behavior
	  @ ensures a >= b ==> \result == a;
	  @ ensures b >= a ==> \result == b;
	  @*/
    public static int max(int a, int b) {
        return (a >= b) ? a : b;
    }


    /**
     * Returns the smaller of two {@code int} values. That is,
     * the result the argument closer to the value of
     * {@link Integer#MIN_VALUE}.  If the arguments have the same
     * value, the result is that same value.
     *
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the smaller of {@code a} and {@code b}.
     */
	/*@ public normal_behavior
	  @ ensures a <= b ==> \result == a;
	  @ ensures b <= a ==> \result == b;
	  @*/
    public static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

}
