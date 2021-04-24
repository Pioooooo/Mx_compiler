package util.list;

public interface ListNode<NodeTy> {
    ListNode<NodeTy> getPrev();

    ListNode<NodeTy> getNext();

    void setPrev(ListNode<NodeTy> prev);

    void setNext(ListNode<NodeTy> next);

    NodeTy get();

    void removeSelf();

    default boolean inList() {
        return (getNext() == null || getNext().getPrev() == this) && (getPrev() == null || getPrev().getNext() == this);
    }
}
