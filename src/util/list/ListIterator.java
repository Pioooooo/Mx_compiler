package util.list;

import java.util.NoSuchElementException;

public class ListIterator<NodeTy> implements java.util.ListIterator<NodeTy> {
    List<NodeTy> list;
    ListNode<NodeTy> cur;

    public ListIterator(List<NodeTy> list, ListNode<NodeTy> cur) {
        this.list = list;
        this.cur = cur;
    }

    public ListIterator(List<NodeTy> list) {
        this.list = list;
        this.cur = list.head;
    }

    public List<NodeTy> getList() {
        return list;
    }

    @Override
    public boolean hasNext() {
        return list.tail != null && cur != list.tail.getNext();
    }

    @Override
    public NodeTy next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        NodeTy dat = cur.get();
        cur = cur.getNext();
        return dat;
    }

    @Override
    public boolean hasPrevious() {
        return (cur == null || cur.getPrev() != null) && list.tail != null;
    }

    @Override
    public NodeTy previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        cur = cur == null ? list.tail : cur.getPrev();
        return cur.get();
    }

    @Override
    public int nextIndex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int previousIndex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove() {
        if (hasPrevious()) {
            cur.getPrev().setNext(cur.getNext());
        }
        if (hasNext()) {
            cur.getNext().setPrev(cur.getPrev());
        }
    }

    @Override
    public void set(NodeTy nodeTy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(NodeTy nodeTy) {
        throw new UnsupportedOperationException();
    }

    public NodeTy get() {
        return cur != null ? cur.get() : null;
    }
}
