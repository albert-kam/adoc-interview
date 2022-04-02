package coda;

import coda.CodaCsvParserConfig.Builder;

public interface ProjectFactory {

	CodaCsvConverter getConverter();

	CodaCsvConverter.Builder<?> getConverterBuilder();

	CodaCsvParser getParserImpl(CodaCsvField... fields);

	CodaCsvParser.Builder<?> getParserBuilder(CodaCsvField... fields);

	CodaOutWriter getOutWriter();

	AppCsv2json getAppCsv2json();

	Builder getParserConfigBuilder(CodaCsvField...fields);

}