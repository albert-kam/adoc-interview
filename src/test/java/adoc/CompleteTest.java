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

import coda.AppCsv2json;
import coda.CodaCsv2jsonConverter;
import coda.CodaCsv2xmlConverter;
import coda.CodaCsvApacheCommonsParser;
import coda.CodaCsvConverter;
import coda.CodaCsvField;
import coda.CodaCsvParserConfig;
import coda.CodaCsvRecords;
import coda.CodaFileOutWriter;
import coda.ConvertedCodaCsvRecords;
import coda.FieldRuleEmail;
import coda.FieldRuleNumber;
import coda.FieldRulePhone;
import coda.FieldRuleString;
import coda.ProjectContext;
import coda.ProjectFactory;
import coda.Version;
import coda.WriteRecordResults;

/**
 * - builder pattern
 * - IoC -- factory pattern
 * - singleton pattern
 * - strategy pattern
 * - SRP
 * - immutability -- getter + never return the modifiable instance like the private List
 * - defensive programming
 * - loosely coupling
 * @author moonblade
 *
 */
public class CompleteTest {

	/**
	 *
1. A user must be able to run a program and feed it a location to a file that has CSV data
2. The system must be able to parse each line of the CSV into a record of data
3. The system must output the formatted record to a separate text file for each row of data (json 
or xml format)
4. Each formatted record should include the name, and value for each of the record's fields with 
support
	 */
	@Test
	public void testComplete_withoutSpecificFieldTypeRule() {
		File file = AppCsv2json.builder()
				.withFileValidators(Lists.newArrayList(
						f -> checkState(f.exists() && f.isFile(), "non existing file %s", f.getAbsolutePath()),
						f -> checkState(f.getName().toLowerCase().endsWith(".csv"))
					))
				.build()
				.loadFile("src/test/resources/input.csv");
		
		CodaCsvRecords recs = CodaCsvApacheCommonsParser.builder()
				.withConfiguration(
						CodaCsvParserConfig.builder()
							.withDefaultFieldRule(FieldRuleString.builder().build())
							.withFirstRowIsHeaderLine(true)
							.withValueSeparator(',')
						.build())
				.build()
				.parse(file);
		
		ConvertedCodaCsvRecords converted = CodaCsv2jsonConverter.builder()
				.withDefaultConversion((key, value) -> String.format("%s: \"%s\"", key, value))
				.build()
				.convert(recs);
		
		WriteRecordResults results = CodaFileOutWriter.builder()
				.withFilenameSuffix(".json")
				.build()
				.write(converted);
		
		assertEquals(getFileContents(results.getOutFiles()), 
				getFileContents(file("src/test/resources/output_line1.txt"),
						file("src/test/resources/output_line2.txt")));
	}

	/**
5. Extend the formatted record to also include either the String or Number data type for each of 
the record's fields with support
	 */
	@Test
	public void testComplete_withStringAndNumberFieldTypeRule() {
		File file = AppCsv2json.builder()
				.withFileValidators(Lists.newArrayList(
						f -> checkState(f.exists() && f.isFile(), "non existing file %s", f.getAbsolutePath()),
						f -> checkState(f.getName().toLowerCase().endsWith(".csv"))
					))
				.build()
				.loadFile("src/test/resources/input.csv");
		
		CodaCsvRecords recs = CodaCsvApacheCommonsParser.builder()
				.withConfiguration(
						CodaCsvParserConfig.builder()
							.withDefaultFieldRule(FieldRuleString.builder().build())
							.withFirstRowIsHeaderLine(true)
							.withValueSeparator(',')
							.withFields(Lists.newArrayList(
									CodaCsvField.of("first_name", 
											FieldRuleString.builder()
												.withRequired(true)
												.build()),
									CodaCsvField.of("zip", 
											FieldRuleNumber.builder()
												.withIntegerMode(true)
												.build())
							))
						.build())
				.build()
				.parse(file);
		
		ConvertedCodaCsvRecords converted = CodaCsv2jsonConverter.builder()
				.withFieldRuleConversion(FieldRuleString.class, (key, value) -> String.format("%s: \"%s\"", key, value))	
				.withFieldRuleConversion(FieldRuleNumber.class, (key, value) -> String.format("%s: %s", key, value))
				.withDefaultConversion((key, value) -> String.format("%s: \"%s\"", key, value))
				.build()
				.convert(recs);
		
		WriteRecordResults results = CodaFileOutWriter.builder()
				.withFilenameSuffix(".json")
				.build()
				.write(converted);
		
		assertEquals(getFileContents(results.getOutFiles()), 
				getFileContents(file("src/test/resources/output_line1_number.txt"),
						file("src/test/resources/output_line2_number.txt")));
	}

	/**
6. Extend the formatted record to also include the Phone number and Email address datatypes for 
each record
	 */
	@Test
	public void testComplete_withEmailAndPhoneFieldTypeRule() {
		File file = AppCsv2json.builder()
				.withFileValidators(Lists.newArrayList(
						f -> checkState(f.exists() && f.isFile(), "non existing file %s", f.getAbsolutePath()),
						f -> checkState(f.getName().toLowerCase().endsWith(".csv"))
					))
				.build()
				.loadFile("src/test/resources/input.csv");
		
		FieldRulePhone fieldRulePhone = FieldRulePhone.builder()
			.withPattern("^\\d{3}-\\d{3}-\\d{4}$")
			.build();
		CodaCsvRecords recs = CodaCsvApacheCommonsParser.builder()
				.withConfiguration(
						CodaCsvParserConfig.builder()
							.withDefaultFieldRule(FieldRuleString.builder().build())
							.withFirstRowIsHeaderLine(true)
							.withValueSeparator(',')
							.withFields(Lists.newArrayList(
									CodaCsvField.of("first_name", 
											FieldRuleString.builder()
												.withRequired(true)
												.build()),
									CodaCsvField.of("zip", 
											FieldRuleNumber.builder()
												.withIntegerMode(true)
												.build()),
									CodaCsvField.of("phone1", fieldRulePhone),
									CodaCsvField.of("phone2", fieldRulePhone),
									CodaCsvField.of("email", 
											FieldRuleEmail.builder()
												.withExcludedDomains("yahoo.com", "hacker.com")
												.build())
							))
						.build())
				.build()
				.parse(file);
		
		ConvertedCodaCsvRecords converted = CodaCsv2jsonConverter.builder()
				.withFieldRuleConversion(FieldRuleString.class, (key, value) -> String.format("%s: \"%s\"", key, value))	
				.withFieldRuleConversion(FieldRuleNumber.class, (key, value) -> String.format("%s: %s", key, value))
				.withDefaultConversion((key, value) -> String.format("%s: \"%s\"", key, value))
				.build()
				.convert(recs);
		
		WriteRecordResults results = CodaFileOutWriter.builder()
				.withFilenameSuffix(".json")
				.build()
				.write(converted);
		
		assertEquals(getFileContents(results.getOutFiles()), 
				getFileContents(file("src/test/resources/output_line1_number.txt"),
						file("src/test/resources/output_line2_number.txt")));
	}

	/**
	 * assuming there's a project-wide configuration
	 */
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

	/**
	 * adding a custom behavior on an existing project-wide configuration
	 */
	@Test
	public void testAppV1_withEmailAndPhoneNumberFieldRules() {
		ProjectFactory factory = ProjectContext.get()
				.getFactory(Version.V1);
		
		CodaCsvConverter csvConverter = ((CodaCsv2jsonConverter.Builder) factory.getConverterBuilder())
					.withFieldRuleConversion(FieldRulePhone.class, (key, value) -> String.format("%s: \"%s\"", key, value))
					.withFieldRuleConversion(FieldRuleEmail.class, (key, value) -> String.format("%s: \"%s\"", key, value))
					.build();
		

		FieldRulePhone fieldRulePhone = FieldRulePhone.builder()
				.withPattern("^\\d{3}-\\d{3}-\\d{4}$")
				.build();
		assertEquals(getFileContents(
				factory.getOutWriter()
					.write(csvConverter.convert(factory.getParserImpl(
									CodaCsvField.of("first_name", FieldRuleString.builder().build()),
									CodaCsvField.of("zip", FieldRuleNumber.builder().build()),
									CodaCsvField.of("phone1", fieldRulePhone),
									CodaCsvField.of("phone2", fieldRulePhone),
									CodaCsvField.of("email", FieldRuleEmail.builder().build())
								)
								.parse(factory.getAppCsv2json()
											.loadFile("src/test/resources/input.csv"))))
					.getOutFiles()
			), 
			getFileContents(file("src/test/resources/output_line1.txt"),
					file("src/test/resources/output_line2.txt")));
	}

	/**
	 * assuming there's a new project-wide configuration aka V2 + custom to output to xml
	 */
	@Test
	public void testAppV2_withXmlWriterQuickTest() {
		ProjectFactory factory = ProjectContext.get()
				.getFactory(Version.V2);
		
		CodaCsvConverter xmlConverter = CodaCsv2xmlConverter.builder()
					.withFieldRuleConversion(FieldRuleString.class, (key, value) -> String.format("<%s>%s</%s>", key, value, key))	
					.withDefaultConversion((key, value) -> String.format("<%s>%s</%s>", key, value, key))
					.withFieldRuleConversion(FieldRulePhone.class, (key, value) -> String.format("<%s>%s</%s>", key, value, key))
					.withFieldRuleConversion(FieldRuleEmail.class, (key, value) -> String.format("<%s>%s</%s>", key, value, key))
					.build();
		
		FieldRulePhone fieldRulePhone = FieldRulePhone.builder()
				.withPattern("^\\d{3}-\\d{3}-\\d{4}$")
				.build();
		assertEquals(getFileContents(
				factory.getOutWriter()
					.write(xmlConverter.convert(factory.getParserImpl(
									CodaCsvField.of("first_name", FieldRuleString.builder().build()),
									CodaCsvField.of("zip", FieldRuleNumber.builder().build()),
									CodaCsvField.of("phone1", fieldRulePhone),
									CodaCsvField.of("phone2", fieldRulePhone),
									CodaCsvField.of("email", FieldRuleEmail.builder().build())
								)
								.parse(factory.getAppCsv2json()
											.loadFile("src/test/resources/input.csv"))))
					.getOutFiles()), 
			getFileContents(file("src/test/resources/output_line1_xml.txt"),
					file("src/test/resources/output_line2_xml.txt")));
	}

}
