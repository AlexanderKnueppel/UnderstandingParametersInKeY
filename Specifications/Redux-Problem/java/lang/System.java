/*
 * Copyright (c) 1994, 2007, Oracle and/or its affiliates. All rights reserved.
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
 * The <code>System</code> class contains several useful class fields
 * and methods. It cannot be instantiated.
 *
 * <p>Among the facilities provided by the <code>System</code> class
 * are standard input, standard output, and error output streams;
 * access to externally defined properties and environment
 * variables; a means of loading files and libraries; and a utility
 * method for quickly copying a portion of an array.
 *
 * @author  unascribed
 * @since   JDK1.0
 */
public final class System {


    /**
     * Returns the current value of the most precise available system
     * timer, in nanoseconds.
     *
     * <p>This method can only be used to measure elapsed time and is
     * not related to any other notion of system or wall-clock time.
     * The value returned represents nanoseconds since some fixed but
     * arbitrary time (perhaps in the future, so values may be
     * negative).  This method provides nanosecond precision, but not
     * necessarily nanosecond accuracy. No guarantees are made about
     * how frequently values change. Differences in successive calls
     * that span greater than approximately 292 years (2<sup>63</sup>
     * nanoseconds) will not accurately compute elapsed time due to
     * numerical overflow.
     *
     * <p> For example, to measure how long some code takes to execute:
     * <pre>
     *   long startTime = System.nanoTime();
     *   // ... the code being measured ...
     *   long estimatedTime = System.nanoTime() - startTime;
     * </pre>
     *
     * @return The current value of the system timer, in nanoseconds.
     * @since 1.5
     */
    public static native long nanoTime();

    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * array referenced by <code>src</code> to the destination array
     * referenced by <code>dest</code>. The number of components copied is
     * equal to the <code>length</code> argument. The components at
     * positions <code>srcPos</code> through
     * <code>srcPos+length-1</code> in the source array are copied into
     * positions <code>destPos</code> through
     * <code>destPos+length-1</code>, respectively, of the destination
     * array.
     * <p>
     * If the <code>src</code> and <code>dest</code> arguments refer to the
     * same array object, then the copying is performed as if the
     * components at positions <code>srcPos</code> through
     * <code>srcPos+length-1</code> were first copied to a temporary
     * array with <code>length</code> components and then the contents of
     * the temporary array were copied into positions
     * <code>destPos</code> through <code>destPos+length-1</code> of the
     * destination array.
     * <p>
     * If <code>dest</code> is <code>null</code>, then a
     * <code>NullPointerException</code> is thrown.
     * <p>
     * If <code>src</code> is <code>null</code>, then a
     * <code>NullPointerException</code> is thrown and the destination
     * array is not modified.
     * <p>
     * Otherwise, if any of the following is true, an
     * <code>ArrayStoreException</code> is thrown and the destination is
     * not modified:
     * <ul>
     * <li>The <code>src</code> argument refers to an object that is not an
     *     array.
     * <li>The <code>dest</code> argument refers to an object that is not an
     *     array.
     * <li>The <code>src</code> argument and <code>dest</code> argument refer
     *     to arrays whose component types are different primitive types.
     * <li>The <code>src</code> argument refers to an array with a primitive
     *    component type and the <code>dest</code> argument refers to an array
     *     with a reference component type.
     * <li>The <code>src</code> argument refers to an array with a reference
     *    component type and the <code>dest</code> argument refers to an array
     *     with a primitive component type.
     * </ul>
     * <p>
     * Otherwise, if any of the following is true, an
     * <code>IndexOutOfBoundsException</code> is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The <code>srcPos</code> argument is negative.
     * <li>The <code>destPos</code> argument is negative.
     * <li>The <code>length</code> argument is negative.
     * <li><code>srcPos+length</code> is greater than
     *     <code>src.length</code>, the length of the source array.
     * <li><code>destPos+length</code> is greater than
     *     <code>dest.length</code>, the length of the destination array.
     * </ul>
     * <p>
     * Otherwise, if any actual component of the source array from
     * position <code>srcPos</code> through
     * <code>srcPos+length-1</code> cannot be converted to the component
     * type of the destination array by assignment conversion, an
     * <code>ArrayStoreException</code> is thrown. In this case, let
     * <b><i>k</i></b> be the smallest nonnegative integer less than
     * length such that <code>src[srcPos+</code><i>k</i><code>]</code>
     * cannot be converted to the component type of the destination
     * array; when the exception is thrown, source array components from
     * positions <code>srcPos</code> through
     * <code>srcPos+</code><i>k</i><code>-1</code>
     * will already have been copied to destination array positions
     * <code>destPos</code> through
     * <code>destPos+</code><i>k</I><code>-1</code> and no other
     * positions of the destination array will have been modified.
     * (Because of the restrictions already itemized, this
     * paragraph effectively applies only to the situation where both
     * arrays have component types that are reference types.)
     *
     * @param      src      the source array.
     * @param      srcPos   starting position in the source array.
     * @param      dest     the destination array.
     * @param      destPos  starting position in the destination data.
     * @param      length   the number of array elements to be copied.
     * @exception  IndexOutOfBoundsException  if copying would cause
     *               access of data outside array bounds.
     * @exception  ArrayStoreException  if an element in the <code>src</code>
     *               array could not be stored into the <code>dest</code> array
     *               because of a type mismatch.
     * @exception  NullPointerException if either <code>src</code> or
     *               <code>dest</code> is <code>null</code>.
     */
    public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);

	/*@ public normal_behavior
	  @ requires src != null;
	  @ requires dst != null;
	  @ requires (length + src_position) <= src.length;
	  @ requires (length + dst_position) <= dst.length;
	  @ ensures dst != null;
	  @ ensures \old(dst.length) == dst.length;
	  @ ensures (\forall int i; 0 <= i && i < dst_position; \old(dst[i]) == dst[i]);
	  @ ensures (\forall int i; dst_position <= i && i < dst_position + length; \old(src[src_position + i - dst_position]) == dst[i]);
	  @ ensures (\forall int i; dst_position + length <= i && i < dst.length; \old(dst[i]) == dst[i]);
	  @ ensures dst == \old(dst);
	  @ assignable dst;
	  @
	  @ also
	  @
	  @ public exceptional_behavior
	  @ requires src == null || dst == null;
	  @ signals_only NullPointerException;
	  @ signals (NullPointerException e) true;
	  @
	  @ also
	  @
	  @ public exceptional_behavior
	  @ requires (length + src_position) > src.length ||
	  @          (length + dst_position) > dst.length ;
	  @ signals_only IndexOutOfBoundsException;
	  @ signals (IndexOutOfBoundsException e) true;
	  @*/
    public static native void arraycopy(/*@ nullable @*/ Object[] src, int src_position,
                                        /*@ nullable @*/ Object[] dst, int dst_position,
                                        int length);
}
