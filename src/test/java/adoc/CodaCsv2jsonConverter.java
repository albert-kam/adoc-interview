package adoc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public class CodaCsv2jsonConverter implements CodaCsvConverter {

	private BiFunction<String, String, String> defaultConversionFun;
	private Map<Class<? extends CodaFieldRule>,
			BiFunction<String, String, String>> rules;
	
	public CodaCsv2jsonConverter(Builder builder) {
		defaultConversionFun = checkNotNull(builder.defaultConversionFun);
		rules = checkNotNull(builder.rules);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends CodaCsvConverter.Builder<Builder> {

		private BiFunction<String, String, String> defaultConversionFun;
		private Map<Class<? extends CodaFieldRule>,
				BiFunction<String, String, String>> rules = Maps.newLinkedHashMap();

		@Override
		public CodaCsvConverter build() {
			return new CodaCsv2jsonConverter(this);
		}

		public Builder withFieldRuleConversion(Class<? extends CodaFieldRule> type,
				BiFunction<String, String, String> fun) {
			rules.put(type, fun);
			return this;
		}

		public Builder withDefaultConversion(BiFunction<String, String, String> defaultConversionFun) {
			this.defaultConversionFun = defaultConversionFun;
			return this;
		}
		
	}

	@Override
	public ConvertedCodaCsvRecords convert(CodaCsvRecords recs) {
		Joiner joiner = Joiner.on(", ");
		return ConvertedCodaCsvRecords.builder()
				.withConvertedRecs(recs.getRecs()
						.stream()
						.map(rec -> String.format("{ %s }", 
								joiner.join(rec.getCells()
									.stream()
									.map(cell -> getFormatter(cell).apply(cell.getKey(), cell.getValue()))
									.collect(Collectors.toList()))
								))
						.collect(Collectors.toList()))
				.build();
	}

	private BiFunction<String, String, String> getFormatter(CodaCsvRecordCell cell) {
		return rules.getOrDefault(cell.getFieldRule(), defaultConversionFun);
	}
}
