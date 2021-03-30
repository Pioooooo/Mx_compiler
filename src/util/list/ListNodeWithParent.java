package util.list;

public interface ListNodeWithParent<NodeTy, ParentTy> extends ListNode<NodeTy> {
    ParentTy getParent();
    void setParent(ParentTy parent);
}
