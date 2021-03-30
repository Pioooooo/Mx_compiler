package util.list;

import java.util.Iterator;

public class List<NodeTy> implements Iterable<NodeTy> {
    ListNode<NodeTy> head, tail;

    public ListIterator<NodeTy> add(ListNode<NodeTy> e) {
        if (head == null) {
            head = tail = e;
        } else {
            e.setPrev(tail);
            tail.setNext(e);
            tail = e;
        }
        return new ListIterator<>(this, e);
    }

    public void remove(ListNode<NodeTy> e) {
        if (e.getNext() != null) {
            e.getNext().setPrev(e.getPrev());
        } else {
            tail = e.getPrev();
        }
        if (e.getPrev() != null) {
            e.getPrev().setNext(e.getNext());
        } else {
            head = e.getNext();
        }
    }

    public ListIterator<NodeTy> insertBefore(ListNode<NodeTy> i, ListNode<NodeTy> e) {
        if (i == null) {
            return add(e);
        }
        if (i == head) {
            head = e;
        }
        e.setPrev(i.getPrev());
        if (i.getPrev() != null) {
            i.getPrev().setNext(e);
        }
        e.setNext(i);
        i.setPrev(e);
        return new ListIterator<>(this, e);
    }

    ListNode<NodeTy> getPrev(ListNode<NodeTy> i) {
        return i == null ? tail : i.getPrev();
    }

    public ListIterator<NodeTy> getHead() {
        return new ListIterator<NodeTy>(this);
    }

    public ListIterator<NodeTy> getTail() {
        return new ListIterator<NodeTy>(this, null);
    }

    @Override
    public Iterator<NodeTy> iterator() {
        return new ListIterator<NodeTy>(this);
    }
}
