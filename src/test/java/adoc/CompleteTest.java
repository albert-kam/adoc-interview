package adoc;

import static com.google.common.base.Preconditions.checkState;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

public class CompleteTest {

	@Test
	public void testAppV1() {
		ProjectFactory factory = ProjectContext.get()
				.getFactory(Version.V1);
		
		File file = factory.getAppCsv2json()
			.loadFile("src/test/resources/input.csv");
		
		CodaCsvRecords recs = factory.getParserImpl(
				CodaCsvField.of("first_name", FieldRuleString.builder().build()),
				CodaCsvField.of("zip", FieldRuleNumber.builder().build())
			)
			.parse(file);
		
		ConvertedCodaCsvRecords converted = factory.getConverter()
			.convert(recs);
		
		WriteRecordResults results = factory.getOutWriter()
			.write(converted);
		
		assertEquals(getFileContents(results.getOutFiles()), 
			getFileContents(file("src/test/resources/output_line1.txt"),
					file("src/test/resources/output_line2.txt")));
	}

	private List<String> getFileContents(File...files) {
		return getFileContents(Lists.newArrayList(files));
	}
	private List<String> getFileContents(List<File> files) {
		Joiner joiner = Joiner.on("\n");
		List<String> list = files.stream()
				.map(f -> {
					try {
						return joiner.join(Files.readLines(f, Charsets.UTF_8));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());
		System.out.println(list);
		return list;
	}

	private File file(String path) {
		return new File(path);
	}

//	@Test
	public void testAppV1_withEmailAndPhoneNumberFieldRules() {
		ProjectFactory factory = ProjectContext.get()
				.getFactory(Version.V1);
		
		CodaCsvConverter csvConverter = ((CodaCsv2jsonConverter.Builder) factory.getConverterBuilder())
					.withFieldRuleConversion(FieldRulePhone.class, (key, value) -> String.format("%s: \"%s\"", key, value))
					.withFieldRuleConversion(FieldRuleEmail.class, (key, value) -> String.format("%s: \"%s\"", key, value))
					.build();
		
		
		assertEquals(getFileContents(
				factory.getOutWriter()
					.write(csvConverter.convert(factory.getParserImpl(
									CodaCsvField.of("first_name", FieldRuleString.builder().build()),
									CodaCsvField.of("zip", FieldRuleNumber.builder().build()),
									CodaCsvField.of("phone1", FieldRulePhone.builder().build()),
									CodaCsvField.of("phone2", FieldRulePhone.builder().build()),
									CodaCsvField.of("email", FieldRuleEmail.builder().build())
								)
								.parse(factory.getAppCsv2json()
											.loadFile("input.csv"))))
					.getOutFiles()
			), 
			getFileContents(file("output_line1.txt"),
					file("output_line2.txt")));
	}

//	@Test
//	public void testAppV2_withXmlWriterQuickTest() {
//		ProjectFactory factory = ProjectContext.get()
//				.getFactory(Version.V2);
//		
//		CodaCsvConverter xmlConverter = CodaCsvConverter.builder()
//			.withConverterImpl(CodaCsv2xmlConverter.builder()
//					.withFieldRuleConversion(FieldRuleString.class, (key, value) -> String.format("<%s>%s</%s>", key, value, key))	
//					.withDefaultConversion((key, value) -> String.format("<%s>%s</%s>", key, value, key))
//					.withFieldRuleConversion(FieldRulePhone.class, (key, value) -> String.format("<%s>%s</%s>", key, value, key))
//					.withFieldRuleConversion(FieldRuleEmail.class, (key, value) -> String.format("<%s>%s</%s>", key, value, key))
//					.build())
//			.build();
//		
//		assertEquals(getFileContents(
//				factory.getOutWriter()
//					.write(xmlConverter.convert(CodaCsvParser.builder()
//								.withParserImpl(factory.getParserImpl(
//										CodaCsvField.of("first_name", FieldRuleString.builder().build()),
//										CodaCsvField.of("zip", FieldRuleNumber.builder().build()),
//										CodaCsvField.of("phone1", FieldRulePhone.builder().build()),
//										CodaCsvField.of("phone2", FieldRulePhone.builder().build()),
//										CodaCsvField.of("email", FieldRuleEmail.builder().build())
//								))
//								.build()
//								.parse(factory.getAppCsv2json()
//											.loadFile("input.csv"))))
//					.getOutFiles()), 
//			getFileContents(file("output_line1.txt"),
//					file("output_line2.txt")));
//	}

}
