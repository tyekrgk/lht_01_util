package lht.wangtong.core.utils.fullseach;


public class DocumentList extends java.util.ArrayList<Document> {
	
	private static final long serialVersionUID = 1L;

	public DocumentList() {
        this(10);
    }

    public DocumentList(int initialCapacity){
        super(initialCapacity);
    }

    private long numFound;
    private long start;

    public long getNumFound() {
        return numFound;
    }

    public void setNumFound(long numFound) {
        this.numFound = numFound;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
