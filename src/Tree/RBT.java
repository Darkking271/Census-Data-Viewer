/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tree;

/**
 *
 * @author Alex <your.name at your.org>
 */
public class RBT 
{
    private Node root;
    
    static class Node
    {
        String item;
        Node left;
        Node right;
        Node parent;
        boolean red;
        Node(String key, Node l, Node r, Node p, boolean i)
        {
            item = key;
            left = l;
            right = r;
            parent = p;
            red = i;
        }
    }
    
    private void rotateRR(Node a, Node b, Node c)
    {
        Node w = a.left;
        Node x = a.right;
        Node y = b.right;
        Node z = c.right;
        boolean ared = a.red;
        boolean bred = b.red;
        boolean cred = c.red;
        c.left =  y;
        if (y != null)
            y.parent = c;
        b.right = c;
        Node cp = c.parent;
        c.parent = b;
        b.parent = cp;
        c.red = true;
        b.red = c.red;
        a.red = false;
        c.red = false;
        if (c != null)
            b.red = true;
        else if (b.parent == null)
            root = b;
    }
    
    Node find(String key)
    {
        Node t = root;
        while (t != null)
        {
            int c = key.compareTo(t.item);
            if (c == 0)
                return t;
            else if (c < 0)
                t = t.left;
            else
                t = t.right;
        }
        return null;
    }
    
    void add(String key)
    {
        Node t = root;
        if (t == null)
            root = new Node(key, null, null, null, false);
        else
        {
            for(;;)
            {
                int c = key.compareTo(t.item);
                if (c == 0)
                    return;
                else if (c < 0)
                {
                    if (t.left != null)
                        t = t.left;
                    else
                    {
                        Node p = new Node(key, null, null, t, true);
                        t.left = p;
                        fixAfterInsert(t, p);
                    }
                }
                else
                {
                    if (t.right != null)
                        t = t.right;
                    else
                    {
                        Node p = new Node(key, null, null, t, true);
                        t.right = p;
                        fixAfterInsert(t, p);
                    }
                }
            }
        }
    }
    
    public void fixAfterInsert(Node p, Node c)
    {
        for (;;)
        {
            if (p == root)
            {
                p.red = false;
                return;
            }
            Node pl = p.left;
            Node pr = p.right;
            if (pl != null)
            {
                if(pr != null)
                {
                    if (pl.red && pr.red)
                    {
                        
                    }
                }
            }
        }
    }
}
