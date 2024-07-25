public abstract class EntryDecorator implements Entry {
    protected Entry next;

    public EntryDecorator(Entry next) {
        this.next = next;
    }

    @Override 
    public void addToCaption(String additionalMetadata) {
        next.addToCaption(additionalMetadata);
    }

    @Override
    public String getImageName() {
        return next.getImageName();
    }

    @Override
    public String getCaption() {
        return next.getCaption();
    }
}
