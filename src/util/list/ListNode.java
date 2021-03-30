package util.list;

public interface ListNode<NodeTy> {
    ListNode<NodeTy> getPrev();

    ListNode<NodeTy> getNext();

    void setPrev(ListNode<NodeTy> prev);

    void setNext(ListNode<NodeTy> next);

    NodeTy get();
}
