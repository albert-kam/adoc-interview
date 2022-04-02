package coda;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.Lists;

import coda.CodaCsvConverter.Builder;

public class ProjectFactoryV1 implements ProjectFactory {

	private static volatile ProjectFactory instance;
	private CodaCsvConverter defaultCsvConverter;
	private CodaOutWriter defaultOutWriter;

	private ProjectFactoryV1() {
		defaultCsvConverter = getConverterBuilder().build();
		defaultOutWriter = getWriterImplBuilder().build();
	}

	public static ProjectFactory get() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (ProjectContext.class) {
				if (instance == null) {
					instance = new ProjectFactoryV1();
				}
			}
		}
		return instance;
	}
	
	@Override
	public CodaCsvConverter getConverter() {
		return defaultCsvConverter;
	}
	@Override
	public CodaCsv2jsonConverter.Builder getConverterBuilder() {
		return CodaCsv2jsonConverter.builder()
			.withFieldRuleConversion(FieldRuleString.class, (key, value) -> String.format("%s: \"%s\"", key, value))
			.withFieldRuleConversion(FieldRuleNumber.class, (key, value) -> String.format("%s: \"%s\"", key, value))
			.withDefaultConversion((key, value) -> String.format("%s: \"%s\"", key, value))
		;
	}

	private CodaOutWriter.Builder<?> getWriterImplBuilder() {
		return CodaFileOutWriter.builder()
				.withFilenameSuffix(".json");
	}

	@Override
	public CodaCsvParser getParserImpl(CodaCsvField...fields) {
		return getParserBuilder(fields).build();
	}
	@Override
	public CodaCsvParser.Builder<?> getParserBuilder(CodaCsvField...fields) {
		return CodaCsvApacheCommonsParser.builder()
			.withConfiguration(getParserConfigBuilder(fields).build())	
		;
	}
	@Override
	public CodaCsvParserConfig.Builder getParserConfigBuilder(CodaCsvField...fields) {
		return CodaCsvParserConfig.builder()
				.withDefaultFieldRule(FieldRuleString.builder().build())
				.withFirstRowIsHeaderLine(true)
				.withValueSeparator(',')
				.withFields(Lists.newArrayList(fields));
	}

	@Override
	public AppCsv2json getAppCsv2json() {
		return AppCsv2json.builder()
				.withFileValidators(Lists.newArrayList(
						f -> checkState(f.exists() && f.isFile(), "non existing file %s", f.getAbsolutePath()),
						f -> checkState(f.getName().toLowerCase().endsWith(".csv"))
					))
				.build();
	}

	@Override
	public CodaOutWriter getOutWriter() {
		return defaultOutWriter;
	}
}
