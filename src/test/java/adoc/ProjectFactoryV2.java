package adoc;

import adoc.CodaCsvConverter.Builder;

public class ProjectFactoryV2 implements ProjectFactory {
	private static volatile ProjectFactoryV2 instance;

	private ProjectFactory v1;

	private ProjectFactoryV2() {
		v1 = ProjectFactoryV1.get();
	}

	public static ProjectFactoryV2 get() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (ProjectFactoryV2.class) {
				if (instance == null) {
					instance = new ProjectFactoryV2();
				}
			}
		}
		return instance;
	}

	@Override
	public CodaCsvConverter getConverter() {
		return v1.getConverter();
	}

	@Override
	public Builder<?> getConverterBuilder() {
		return v1.getConverterBuilder();
	}

	@Override
	public CodaCsvParser getParserImpl(CodaCsvField... fields) {
		return v1.getParserImpl(fields);
	}

	@Override
	public adoc.CodaCsvParser.Builder<?> getParserBuilder(CodaCsvField... fields) {
		return v1.getParserBuilder(fields);
	}

	@Override
	public CodaOutWriter getOutWriter() {
		return v1.getOutWriter();
	}

	@Override
	public AppCsv2json getAppCsv2json() {
		return v1.getAppCsv2json();
	}

	@Override
	public adoc.CodaCsvParserConfig.Builder getParserConfigBuilder(CodaCsvField... fields) {
		return v1.getParserConfigBuilder(fields)
				.withValueSeparator(';');
	}
}
