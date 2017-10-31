package testproject.testproject;

public enum Driver {
	Firefox("firefox"),
	Chrome("TWO");

	private final String text;

	/**
     * @param text
     */
    private Driver(final String text) {
        this.text = text;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
