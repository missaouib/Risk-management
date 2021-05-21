package com.smartosc.fintech.risk.rulemanagement.controller;

import com.smartosc.fintech.risk.rulemanagement.dto.DataModelDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DataModelRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.ListAttributeIdRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.UpdateAttributeRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.AttributeResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.DataModelResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.ErrorResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author vuhq
 * @Since 2/2/2021
 */
@RequestMapping("/${application-short-name}/${version}/data-models")
@Api(value = "data model management")
@Validated
public interface DataModelController {
    @ApiOperation(value = "Delete a Data model")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @DeleteMapping("/{data-model-id}")
    SuccessResponse<String> deleteDataModel(@PathVariable(name = "data-model-id") @NotNull Long dataModelId);

    @ApiOperation(value = "Delete a attribute")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @DeleteMapping("/{data-model-id}/attributes/{attribute-id}")
    SuccessResponse<String> deleteAttribute(@PathVariable(name = "data-model-id") @NotNull @Min(value = 0) Long dataModelId,
                                            @PathVariable(name = "attribute-id") @NotNull @Min(value = 0) Long attributeId);

    @ApiOperation(value = "delete attributes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @PutMapping("/{data-model-id}/attributes")
    SuccessResponse<String> deleteMultiAttribute(@PathVariable(name = "data-model-id") @NotNull @Min(value = 0) Long dataModelId,
                                                 @Valid @RequestBody ListAttributeIdRequest ids);

    @ApiOperation(value = "update a attribute")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @PutMapping("/{data-model-id}/attributes/{attribute-id}")
    SuccessResponse<AttributeResponse> updateAttribute(@PathVariable(name = "data-model-id") @NotNull @Min(value = 0) Long dataModelId,
                                                       @PathVariable(name = "attribute-id") @NotNull @Min(value = 0) Long attributeId,
                                                       @RequestBody @Valid UpdateAttributeRequest request);

    @ApiOperation(value = "Listing all data model")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Page.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @GetMapping("group/{group-id}")
    SuccessResponse<List<DataModelDto>> getDataModelByGroup(@PathVariable(name = "group-id") @NotNull Long groupId);

    @ApiOperation(value = "Get data model by id", notes = "id is mandatory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DataModelDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/{data-model-id}")
    SuccessResponse<DataModelDto> getDataModel(@PathVariable(name = "data-model-id") @NotNull Long dataModelId);

    @CrossOrigin
    @PostMapping
    SuccessResponse<DataModelResponse> createDataModel(@Valid @RequestBody List<DataModelRequest> objectModels);

}
