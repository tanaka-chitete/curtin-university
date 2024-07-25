public class Metadata extends EntryDecorator {
    public Metadata(Entry next) {
        super(next);
    }

    @Override
    public void addToCaption(String GPS) {
        next.addToCaption(GPS);
    }
}
