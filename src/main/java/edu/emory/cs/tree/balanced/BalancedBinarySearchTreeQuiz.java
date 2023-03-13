/*
 * Copyright 2020 Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;

/** @author Jinho D. Choi */
public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    /**
     * balance          Balances a tree based on specific conditions at a specific node
     * @return          The tree will be balanced such that the node's grandparent's left subtree is full
     * @param node      The node of the tree where balancing will begin
     */
    @Override
    protected void balance(BinaryNode<T> node) {
        if (node == null) return;

        // assigning references to family members aids in maintaining references in rotations
        BinaryNode<T> parent = node.getParent();
        BinaryNode<T> uncle = node.getUncle();
        BinaryNode<T> grandParent = node.getGrandParent();

        // If the tree should not be rotated, balanced is finished
        if (!shouldRotate(node, parent, uncle, grandParent)) return;

        // Case examples below are 1D representations of binary trees
        // Case 1) Example: [3, 2, 5, 1, null, 4, null]
        if (uncle.hasLeftChild() && parent.hasLeftChild()) {
            rotateRight(parent);
            rotateLeft(grandParent);
            rotateRight(grandParent);
        }
        // Case 2) Example: [3, 1, 4, null, 2, null, 5]
        else if (uncle.hasRightChild() && parent.hasRightChild()) {
            rotateLeft(uncle);
            rotateLeft(grandParent);
            rotateRight(grandParent);
        }
        // Case 3) Example: [3, 1, 5, null, 2, 4, null]
        else if (uncle.hasRightChild() && parent.hasLeftChild()) {
            rotateRight(parent);
            rotateLeft(uncle);
            rotateLeft(grandParent);
            rotateRight(grandParent);
        }
        // Case 4) Example: [3, 2, 4, 1, null, null, 5]
        else if (uncle.hasLeftChild() && parent.hasRightChild()) {
            rotateLeft(grandParent);
            rotateRight(grandParent);
        }
    }

    /**
     * shouldRotate         Returns if balancing rotations should occur based on certain conditions
     * @return              True if the tree should be rotated, false if not
     * @param node          The node that will be inspected for rotation occurrence
     * @param parent        The parent of the node
     * @param uncle         The uncle of the node
     * @param grandParent   The grandparent of the node
     */
    private boolean shouldRotate(BinaryNode<T> node, BinaryNode<T> parent, BinaryNode<T> uncle, BinaryNode<T> grandParent) {
        // Verify node has a parent, uncle, and grandParent
        if (parent == null || grandParent == null || uncle == null) return false;

        // Verify node is an only child
        if (parent.hasBothChildren() || node.hasLeftChild() || node.hasRightChild()) return false;

        // Verify parent is right child of grandParent
        if (!grandParent.isRightChild(parent)) return false;

        // Following three lines verify node has only one cousin
        if (!(uncle.hasLeftChild() ^ uncle.hasRightChild())) return false;
        BinaryNode<T> cousin = (uncle.hasLeftChild()) ? uncle.getLeftChild() : uncle.getRightChild();
        if (cousin.hasLeftChild() || cousin.hasRightChild()) return false;

        return true;
    }
}