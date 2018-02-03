/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.lang.reflect.*;

/**
 * This class contains various methods for manipulating arrays (such as
 * sorting and searching).  This class also contains a static factory
 * that allows arrays to be viewed as lists.
 *
 * <p>The methods in this class all throw a <tt>NullPointerException</tt> if
 * the specified array reference is null, except where noted.
 *
 * <p>The documentation for the methods contained in this class includes
 * briefs description of the <i>implementations</i>.  Such descriptions should
 * be regarded as <i>implementation notes</i>, rather than parts of the
 * <i>specification</i>.  Implementors should feel free to substitute other
 * algorithms, so long as the specification itself is adhered to.  (For
 * example, the algorithm used by <tt>sort(Object[])</tt> does not have to be
 * a mergesort, but it does have to be <i>stable</i>.)
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @author  John Rose
 * @since   1.2
 */

public class Arrays {
    // Suppresses default constructor, ensuring non-instantiability.
    private Arrays() {
    }

    // Cloning
    /**
     * Copies the specified array, truncating or padding with nulls (if necessary)
     * so the copy has the specified length.  For all indices that are
     * valid in both the original array and the copy, the two arrays will
     * contain identical values.  For any indices that are valid in the
     * copy but not the original, the copy will contain <tt>null</tt>.
     * Such indices will exist if and only if the specified length
     * is greater than that of the original array.
     * The resulting array is of exactly the same class as the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls
     *     to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     * @since 1.6
     */
	/*@ public normal_behavior
	  @ requires original != null;
	  @ requires newLength >= 0;
	  @ ensures \result != null;
	  @ ensures \result.length == newLength;
	  @ ensures (\forall int i; 0 <= i && i < \result.length && i < original.length; original[i] == \result[i]);
	  @ ensures (\forall int i; original.length <= i && i < newLength; \result[i] == null);
	  @ ensures \typeof(\result) == \typeof(original);
	  @ ensures \fresh(\result);
	  @ ensures \result != original;
	  @
	  @ also
	  @
	  @ public exceptional_behavior
	  @ requires original == null;
	  @ signals_only NullPointerException;
	  @ signals (NullPointerException e) true;
	  @
	  @ also
	  @
	  @ public exceptional_behavior
	  @ requires newLength < 0;
	  @ signals_only NegativeArraySizeException;
	  @ signals (NegativeArraySizeException e) true;
	  @*/
    public static /*@ nullable pure@*/ Object[] copyOf(/*@ nullable @*/ Object[] original, int newLength) {
//MOD_FOR_PROOF        return (T[]) copyOf(original, newLength, original.getClass());
    }
	
    /**
     * Copies the specified range of the specified array into a new array.
     * The initial index of the range (<tt>from</tt>) must lie between zero
     * and <tt>original.length</tt>, inclusive.  The value at
     * <tt>original[from]</tt> is placed into the initial element of the copy
     * (unless <tt>from == original.length</tt> or <tt>from == to</tt>).
     * Values from subsequent elements in the original array are placed into
     * subsequent elements in the copy.  The final index of the range
     * (<tt>to</tt>), which must be greater than or equal to <tt>from</tt>,
     * may be greater than <tt>original.length</tt>, in which case
     * <tt>0</tt> is placed in all elements of the copy whose index is
     * greater than or equal to <tt>original.length - from</tt>.  The length
     * of the returned array will be <tt>to - from</tt>.
     *
     * @param original the array from which a range is to be copied
     * @param from the initial index of the range to be copied, inclusive
     * @param to the final index of the range to be copied, exclusive.
     *     (This index may lie outside the array.)
     * @return a new array containing the specified range from the original array,
     *     truncated or padded with zeros to obtain the required length
     * @throws ArrayIndexOutOfBoundsException if {@code from < 0}
     *     or {@code from > original.length}
     * @throws IllegalArgumentException if <tt>from &gt; to</tt>
     * @throws NullPointerException if <tt>original</tt> is null
     * @since 1.6
     */
	 /*@ public normal_behavior
	  @ requires original != null;
	  @ requires to >= from;
	  @ requires from >= 0;
	  @ ensures \result != null;
	  @ ensures \result.length == to-from;
	  @ ensures (\forall int i; 0 <= i && i < to-from && i < original.length-from; original[i+from] == \result[i]);
	  @ ensures (\forall int i; original.length <= i && i < to; 0 == \result[i]);
	  @ ensures \typeof(\result) == \typeof(original);
	  @ ensures \fresh(\result);
	  @ ensures \result != original;
	  @ assignable \nothing;
	  @
	  @ also
	  @
	  @ public exceptional_behavior
	  @ requires original == null;
	  @ signals_only NullPointerException;
	  @ signals (NullPointerException e) true;
	  @
	  @ also
	  @
	  @ public exceptional_behavior
	  @ requires from > to;
	  @ signals_only IllegalArgumentException;
	  @ signals (IllegalArgumentException e) true;
	  @
	  @ also
	  @
	  @ public exceptional_behavior
	  @ requires from < 0 || from > original.length;
	  @ signals_only ArrayIndexOutOfBoundsException;
	  @ signals (ArrayIndexOutOfBoundsException e) true;
	  @*/
    public static int[] copyOfRange(int[] original, int from, int to) {
/*MOD_FOR_PROOF        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        int[] copy = new int[newLength];
        System.arraycopy(original, from, copy, 0,
                         Math.min(original.length - from, newLength));
        return copy;*/
    }

}
