package coda;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import javax.annotation.processing.Generated;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import coda.WriteRecordResults.Builder;

public class CodaFileOutWriter implements CodaOutWriter {

	private String filenameSuffix;

	@Generated("SparkTools")
	private CodaFileOutWriter(Builder builder) {
		filenameSuffix = checkNotNull(builder.filenameSuffix);
	}
	
	@Override
	public WriteRecordResults write(ConvertedCodaCsvRecords recs) {
		WriteRecordResults.Builder builder = WriteRecordResults.builder();
		recs.getConvertedRecs()
			.stream()
			.forEach(recContent -> {
				File outFile;
				try {
					outFile = File.createTempFile("coda", filenameSuffix);
					Files.asCharSink(outFile, Charsets.UTF_8)
						.write(recContent);
					builder.addOutFile(outFile);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
		return builder.build();
	}
	
	/**
	 * Creates builder to build {@link CodaFileOutWriter}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link CodaFileOutWriter}.
	 */
	@Generated("SparkTools")
	public static final class Builder extends CodaOutWriter.Builder<Builder> {
		private String filenameSuffix;

		private Builder() {
		}

		public Builder withFilenameSuffix(String filenameSuffix) {
			this.filenameSuffix = filenameSuffix;
			return this;
		}

		@Override
		public CodaFileOutWriter build() {
			return new CodaFileOutWriter(this);
		}
	}

}
