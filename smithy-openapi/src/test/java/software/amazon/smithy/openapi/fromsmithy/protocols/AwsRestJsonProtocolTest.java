package software.amazon.smithy.openapi.fromsmithy.protocols;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.model.Model;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.openapi.OpenApiConstants;
import software.amazon.smithy.openapi.fromsmithy.OpenApiConverter;
import software.amazon.smithy.openapi.model.OpenApi;
import software.amazon.smithy.utils.IoUtils;

public class AwsRestJsonProtocolTest {
    @Test
    public void addsJsonDocumentBodies() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("adds-json-document-bodies.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create()
                .convert(model, ShapeId.from("smithy.example#Service"));
        Node expectedNode = Node.parse(
                IoUtils.toUtf8String(getClass().getResourceAsStream("adds-json-document-bodies.openapi.json")));

        Node.assertEquals(result, expectedNode);
    }

    @Test
    public void canUseCustomMediaType() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("adds-json-document-bodies.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create()
                .putSetting(OpenApiConstants.AWS_JSON_CONTENT_TYPE, "application/x-amz-json-1.0")
                .convert(model, ShapeId.from("smithy.example#Service"));

        Assertions.assertTrue(Node.printJson(result.toNode()).contains("application/x-amz-json-1.0"));
    }

    @Test
    public void addsProperFormatForPathTimestamps() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("adds-path-timestamp-format.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create().convert(model, ShapeId.from("smithy.example#Service"));
        Node expectedNode = Node.parse(
                IoUtils.toUtf8String(getClass().getResourceAsStream("adds-path-timestamp-format.openapi.json")));

        Node.assertEquals(result, expectedNode);
    }

    @Test
    public void addsProperFormatForQueryTimestamps() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("adds-query-timestamp-format.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create().convert(model, ShapeId.from("smithy.example#Service"));
        Node expectedNode = Node.parse(
                IoUtils.toUtf8String(getClass().getResourceAsStream("adds-query-timestamp-format.openapi.json")));

        Node.assertEquals(result, expectedNode);
    }

    @Test
    public void addsProperFormatForQueryBlobs() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("adds-query-blob-format.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create().convert(model, ShapeId.from("smithy.example#Service"));
        Node expectedNode = Node.parse(
                IoUtils.toUtf8String(getClass().getResourceAsStream("adds-query-blob-format.openapi.json")));

        Node.assertEquals(result, expectedNode);
    }

    @Test
    public void addsProperFormatForHeaderTimestamps() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("adds-header-timestamp-format.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create().convert(model, ShapeId.from("smithy.example#Service"));
        Node expectedNode = Node.parse(
                IoUtils.toUtf8String(getClass().getResourceAsStream("adds-header-timestamp-format.openapi.json")));

        Node.assertEquals(result, expectedNode);
    }

    @Test
    public void addsProperFormatForHeaderMediaTypeStrings() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("adds-header-mediatype-format.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create().convert(model, ShapeId.from("smithy.example#Service"));
        Node expectedNode = Node.parse(
                IoUtils.toUtf8String(getClass().getResourceAsStream("adds-header-mediatype-format.openapi.json")));

        Node.assertEquals(result, expectedNode);
    }

    @Test
    public void supportsRequestAndResponsePaylaods() {
        Model model = Model.assembler()
                .addImport(getClass().getResource("supports-payloads.json"))
                .assemble()
                .unwrap();
        OpenApi result = OpenApiConverter.create().convert(model, ShapeId.from("smithy.example#Service"));
        Node expectedNode = Node.parse(
                IoUtils.toUtf8String(getClass().getResourceAsStream("supports-payloads.openapi.json")));

        Node.assertEquals(result, expectedNode);
    }
}