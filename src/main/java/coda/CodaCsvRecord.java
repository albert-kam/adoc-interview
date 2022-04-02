package coda;

import java.util.Collections;
import java.util.List;

import javax.annotation.processing.Generated;

import com.google.common.collect.Lists;

public class CodaCsvRecord {
	private List<CodaCsvRecordCell> cells;

	@Generated("SparkTools")
	private CodaCsvRecord(Builder builder) {
		cells = builder.cells;
	}

	/**
	 * Creates builder to build {@link CodaCsvRecord}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link CodaCsvRecord}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private List<CodaCsvRecordCell> cells = Lists.newArrayList();

		private Builder() {
		}

		public Builder withCells(List<CodaCsvRecordCell> cells) {
			this.cells = cells;
			return this;
		}

		public CodaCsvRecord build() {
			return new CodaCsvRecord(this);
		}

		public Builder add(CodaCsvRecordCell cell) {
			cells.add(cell);
			return this;
		}
	}

	public List<CodaCsvRecordCell> getCells() {
		return cells;
	}
}
