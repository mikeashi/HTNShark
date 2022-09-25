package JSHOP2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

/** This class represents all the variables that JSHOP2 needs every time it
 *  calls itself recursively. The reason all these variables are bundled
 *  together in one class rather than having them locally defined is to save
 *  stack space. Right now, the only thing that is stored in the stack is a
 *  pointer to this class in each recursion, and the actual data is stored in
 *  heap memory, while if these variables were just locally defined, all of
 *  them would be stored in the stack, resulting in very fast stack overflow
 *  errors.
 *
 *  @author Okhtay Ilghami
 *  @author <a href="http://www.cs.umd.edu/~okhtay">http://www.cs.umd.edu/~okhtay</a>
 *  @version 1.0.3
*/
class InternalVars
{
  /** The binding that unifies the head of a method or an operator with the
   *  task being achieved.
  */
  Term[] binding;

  /** An array of size 4 to store the atoms and protections that are being
   *  deleted or added to the current state of the world as a result of
   *  application of an operator, to be used in case of a backtrack over that
   *  operator.
  */
  Vector[] delAdd;

  /** The iterator iterating over the <code>LinkedList</code> of the tasks
   *  that we have the option to achieve right now.
  */
  Iterator<TaskList> e;

  /** Whether or not at least one satisfier has been found for the current
   *  branch of the current method. As soon as it becomes <code>true</code>,
   *  further branches of the method will not be considered.
  */
  boolean found;

  /** The index of the method or operator being considered.
  */
  int j;

  /** The index of the branch of the current method being considered.
  */
  int k;

  /** An array of methods that can achieve the compound task being
   *  considered.
  */
  Method[] m;

  /** Next binding that satisfies the precondition of the current method or
   *  operator.
  */
  Term[] nextB;

  /** An array of operators that can achieve the primitive task being
   *  considered.
  */
  Operator[] o;

  /** An iterator over the bindings that can satisfy the precondition of the
   *  current method or operator.
  */
  Precondition p;

  /** The task atom chosen to be achieved next.
  */
  TaskAtom t;

  /** A <code>LinkedList</code> of the task atoms we have the option to
   *  achieve right now.
  */
  LinkedList<TaskList> t0;

  /** The atomic task list that represents, in the task network, the task
   *  atom that has been chosen to be achieved next.
  */
  TaskList tl;
}