package adoc;

import java.util.Collections;
import java.util.List;

import javax.annotation.processing.Generated;

import com.google.common.collect.Lists;

public class CodaCsvRecords {
	private List<CodaCsvRecord> recs;

	@Generated("SparkTools")
	private CodaCsvRecords(Builder builder) {
		recs = builder.recs;
	}

	/**
	 * Creates builder to build {@link CodaCsvRecords}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link CodaCsvRecords}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private List<CodaCsvRecord> recs = Lists.newArrayList();

		private Builder() {
		}

		public CodaCsvRecords build() {
			return new CodaCsvRecords(this);
		}

		public Builder add(CodaCsvRecord codaCsvRecord) {
			recs.add(codaCsvRecord);
			return this;
		}
	}

	public List<CodaCsvRecord> getRecs() {
		return Lists.newArrayList(recs);
	}
}
