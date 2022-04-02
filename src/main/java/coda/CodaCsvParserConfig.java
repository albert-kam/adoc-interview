package coda;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CodaCsvParserConfig {
	private boolean firstRowIsHeaderLine;
	private char valueSeparator;
	private Map<String, CodaFieldRule> fields;
	private CodaFieldRule defaultFieldRule;

	@Generated("SparkTools")
	private CodaCsvParserConfig(Builder builder) {
		firstRowIsHeaderLine = builder.firstRowIsHeaderLine;
		valueSeparator = builder.valueSeparator;
		fields = toMap(checkNotNull(builder.fields));
		defaultFieldRule = checkNotNull(builder.defaultFieldRule);
	}
	private Map<String, CodaFieldRule> toMap(List<CodaCsvField> fields) {
		ImmutableMap.Builder<String, CodaFieldRule> builder = new ImmutableMap.Builder<String, CodaFieldRule>();
		fields.stream()
			.forEach(field -> builder.put(field.getCol(), field.getRule()));
		return builder.build();
	}
	/**
	 * Creates builder to build {@link CodaCsvParserConfig}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CodaCsvParserConfig}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private boolean firstRowIsHeaderLine;
		private char valueSeparator;
		private List<CodaCsvField> fields = Lists.newArrayList();
		private CodaFieldRule defaultFieldRule;

		private Builder() {
		}

		public Builder withFirstRowIsHeaderLine(boolean firstRowIsHeaderLine) {
			this.firstRowIsHeaderLine = firstRowIsHeaderLine;
			return this;
		}

		public Builder withValueSeparator(char valueSeparator) {
			this.valueSeparator = valueSeparator;
			return this;
		}

		public Builder withFields(List<CodaCsvField> fields) {
			this.fields = fields;
			return this;
		}
		public Builder withDefaultFieldRule(CodaFieldRule defaultFieldRule) {
			this.defaultFieldRule = defaultFieldRule;
			return this;
		}

		public CodaCsvParserConfig build() {
			return new CodaCsvParserConfig(this);
		}
	}
	public boolean isFirstRowIsHeaderLine() {
		return firstRowIsHeaderLine;
	}
	public char getValueSeparator() {
		return valueSeparator;
	}
	public Map<String, CodaFieldRule> getFields() {
		return fields;
	}
	public CodaFieldRule getDefaultFieldRule() {
		return defaultFieldRule;
	}
	public CodaFieldRule getFieldRule(String col) {
		return fields.getOrDefault(col, defaultFieldRule);
	}
}
