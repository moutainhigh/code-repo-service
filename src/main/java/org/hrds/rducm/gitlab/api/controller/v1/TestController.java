package org.hrds.rducm.gitlab.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.core.oauth.DetailsHelper;
import io.swagger.annotations.ApiOperation;
import org.hrds.rducm.gitlab.app.job.MemberInitJob;
import org.hrds.rducm.gitlab.app.job.MembersAuditJob;
import org.hrds.rducm.gitlab.infra.client.gitlab.Gitlab4jClientWrapper;
import org.hrds.rducm.gitlab.infra.feign.vo.C7nUserVO;
import org.hzero.core.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * todo 需删除
 */
@RestController
@RequestMapping("/v1/gitlab/test")
public class TestController extends BaseController {
//    @Autowired
//    private Gitlab4jClientWrapper gitlab4jClientWrapper;
//
//    @Autowired
//    private RdmMemberAuditRecordServiceImpl securityAudit;
//
//    @Autowired
//    private BaseServiceFeignClient baseServiceFeignClient;

    static Logger logger = LoggerFactory.getLogger(TestController.class);

    @ApiOperation(value = "查询")
    @Permission(level = ResourceLevel.SITE, permissionLogin = true)
    @PostMapping("/users")
    public ResponseEntity<List<C7nUserVO>> queryUser() {
        Long userId = DetailsHelper.getUserDetails().getUserId();

        logger.warn("-------------------- getUserDetails:{}", DetailsHelper.getUserDetails());
        logger.warn("-------------------- userId:{}", userId);
//        return baseServiceFeignClient.listUsersByIds(ids, null);
        return null;
    }

    @ApiOperation(value = "查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/test")
    public ResponseEntity<Long> test() {
        System.out.println("test");
        Long userId = DetailsHelper.getUserDetails().getUserId();

        logger.warn("-------------------- getUserDetails:{}", DetailsHelper.getUserDetails());
        logger.warn("-------------------- userId:{}", userId);

        return ResponseEntity.ok(userId);
    }

    @Autowired
    private MembersAuditJob membersAuditJob;

    @ApiOperation(value = "membersAuditJob")
    @Permission(level = ResourceLevel.SITE, permissionPublic = true)
    @PostMapping("/organizations/{organizationId}/membersAuditJob")
    public void initMembers(@PathVariable Long organizationId) {
        Map<String, Object> map = new HashMap<>();
        map.put("organizationId", organizationId);
        membersAuditJob.audit();
    }
}
