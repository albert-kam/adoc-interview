package adoc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CodaCsvApacheCommonsParser implements CodaCsvParser {
	private CodaCsvParserConfig config;
	private CSVFormat format;
	
	public static class Builder extends CodaCsvParser.Builder<Builder> {
		private CodaCsvParserConfig config;

		@Override
		public CodaCsvParser build() {
			return new CodaCsvApacheCommonsParser(this);
		}

		public Builder withConfiguration(CodaCsvParserConfig config) {
			this.config = config;
			return this;
		}
		
	}
	public CodaCsvApacheCommonsParser(Builder builder) {
		config = checkNotNull(builder.config);
		format = CSVFormat.DEFAULT
			.withIgnoreEmptyLines()
			.withIgnoreHeaderCase()
			.withTrim()
			.withDelimiter(config.getValueSeparator());
		if (config.isFirstRowIsHeaderLine()) {
			format = format.withFirstRecordAsHeader();
		}
	}
	
	@Override
	public CodaCsvRecords parse(File file) {
		CodaCsvRecords.Builder builder = CodaCsvRecords.builder();
		try (CSVParser csvParser = format.parse(new FileReader(file))) {
			List<String> headerNames = csvParser.getHeaderNames();
			for (CSVRecord csvRecord : csvParser.getRecords()) {
				builder.add(toCodaCsvRecord(headerNames, csvRecord));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return builder.build();
	}
	
	private CodaCsvRecord toCodaCsvRecord(List<String> headerNames, CSVRecord csvRecord) {
		CodaCsvRecord.Builder builder = CodaCsvRecord.builder(); 
		for (String col: headerNames) {
			CodaFieldRule fieldRule = checkNotNull(config.getFieldRule(col));
			builder.add(CodaCsvRecordCell.builder()
				.withKey(col)
				.withValue(fieldRule.validate(csvRecord.get(col)))
				.withFieldRule(fieldRule)
				.build());
		}
		return builder.build();
	}

	public static Builder builder() {
		return new Builder();
	}

}
