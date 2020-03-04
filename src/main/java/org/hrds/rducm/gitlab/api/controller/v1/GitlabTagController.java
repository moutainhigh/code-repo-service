package org.hrds.rducm.gitlab.api.controller.v1;

import io.choerodon.core.annotation.Permission;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.models.ProtectedTag;
import org.gitlab4j.api.models.Tag;
import org.hrds.rducm.gitlab.app.service.GitlabTagService;
import org.hrds.rducm.gitlab.infra.constant.ApiInfoConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(tags = SwaggerTags.GITLAB_MEMBER)
@RestController("gitlabTagController.v1")
@RequestMapping("/v1/projects/{projectId}/gitlab/repositories/{repositoryId}/tags")
public class GitlabTagController extends BaseController {
    public static final String API_INFO_TAG_NAME = "标签名(可使用通配符)";
    public static final String API_INFO_CREATE_ACCESS_LEVEL = "是否允许创建-权限级别(0|30|40)";

    @Autowired
    private GitlabTagService gitlabTagService;

    @ApiOperation(value = "查询标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = ApiInfoConstants.PROJECT_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "repositoryId", value = ApiInfoConstants.REPOSITORY_ID, paramType = "path", required = true),
    })
    @Permission(permissionPublic = true)
    @GetMapping
    public ResponseEntity<List<Tag>> getTags(@PathVariable Long projectId,
                                             @PathVariable Long repositoryId) {
        return Results.success(gitlabTagService.getTags(repositoryId));
    }

    @ApiOperation(value = "查询保护标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = ApiInfoConstants.PROJECT_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "repositoryId", value = ApiInfoConstants.REPOSITORY_ID, paramType = "path", required = true),
    })
    @Permission(permissionPublic = true)
    @GetMapping("/protected-tags")
    public ResponseEntity<List<ProtectedTag>> getProtectedTags(@PathVariable Long projectId,
                                                               @PathVariable Long repositoryId) {
        return Results.success(gitlabTagService.getProtectedTags(repositoryId));
    }

    @ApiOperation(value = "创建保护标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = ApiInfoConstants.PROJECT_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "repositoryId", value = ApiInfoConstants.REPOSITORY_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "tagName", value = API_INFO_TAG_NAME, paramType = "query", required = true),
            @ApiImplicitParam(name = "createAccessLevel", value = API_INFO_CREATE_ACCESS_LEVEL, paramType = "query", required = true),
    })
    @Permission(permissionPublic = true)
    @PostMapping("/protected-tags")
    public ResponseEntity<ProtectedTag> createProtectedTag(@PathVariable Long projectId,
                                                           @PathVariable Long repositoryId,
                                                           @RequestParam String tagName,
                                                           @RequestParam Integer createAccessLevel) {
        return Results.success(gitlabTagService.protectTag(repositoryId, tagName, createAccessLevel));
    }

    @ApiOperation(value = "修改保护标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = ApiInfoConstants.PROJECT_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "repositoryId", value = ApiInfoConstants.REPOSITORY_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "tagName", value = "标签名", paramType = "query", required = true),
            @ApiImplicitParam(name = "createAccessLevel", value = API_INFO_CREATE_ACCESS_LEVEL, paramType = "query", required = true),
    })
    @Permission(permissionPublic = true)
    @PutMapping("/protected-tags")
    public ResponseEntity<ProtectedTag> updateProtectedTag(@PathVariable Long projectId,
                                                           @PathVariable Long repositoryId,
                                                           @RequestParam String tagName,
                                                           @RequestParam Integer createAccessLevel) {
        return Results.success(gitlabTagService.protectTag(repositoryId, tagName, createAccessLevel));
    }

    @ApiOperation(value = "删除保护标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = ApiInfoConstants.PROJECT_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "repositoryId", value = ApiInfoConstants.REPOSITORY_ID, paramType = "path", required = true),
            @ApiImplicitParam(name = "tagName", value = "标签名", paramType = "query", required = true),
    })
    @Permission(permissionPublic = true)
    @DeleteMapping("/protected-tags")
    public ResponseEntity<ProtectedTag> deleteProtectedTag(@PathVariable Long projectId,
                                                           @PathVariable Long repositoryId,
                                                           @RequestParam String tagName) {
        gitlabTagService.unprotectTag(repositoryId, tagName);
        return Results.success();
    }
}
