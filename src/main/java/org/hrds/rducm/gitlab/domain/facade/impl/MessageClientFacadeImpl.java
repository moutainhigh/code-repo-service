package org.hrds.rducm.gitlab.domain.facade.impl;

import io.choerodon.core.oauth.DetailsHelper;
import org.hrds.rducm.gitlab.domain.facade.C7nBaseServiceFacade;
import org.hrds.rducm.gitlab.domain.facade.MessageClientFacade;
import org.hrds.rducm.gitlab.infra.enums.IamRoleCodeEnum;
import org.hrds.rducm.gitlab.infra.feign.vo.C7nUserVO;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ying.xie@hand-china.com
 * @date 2020/6/4
 */
@Service
public class MessageClientFacadeImpl implements MessageClientFacade {
    public static final String APPLICANT_TEMPLATE_CODE = "RDUCM_MEMBER_APPLICANT.WEB";

    private static final Logger logger = LoggerFactory.getLogger(MessageClientFacadeImpl.class);

    @Autowired
    private MessageClient messageClient;
    @Autowired
    private C7nBaseServiceFacade c7NBaseServiceFacade;

    /**
     * 申请权限发送站内消息
     * 发送给所有[项目管理员]
     */
    @Override
    public void sendApprovalMessage(Long projectId) {
        // 查询该项目下所有用户
        List<C7nUserVO> c7nUserVOS = c7NBaseServiceFacade.listC7nUsersOnProjectLevel(projectId);
        // 过滤并获取所有"项目管理员"角色的用户
        c7nUserVOS = c7nUserVOS.stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> IamRoleCodeEnum.PROJECT_OWNER.getCode().equals(r.getCode())))
                .collect(Collectors.toList());

        Long tenantId = DetailsHelper.getUserDetails().getTenantId();
        String lang = "zh_CN";
        List<Receiver> receivers = new ArrayList<>();
        Map<String, String> args = new HashMap<>(16);

        c7nUserVOS.forEach(u -> {
            Receiver receiver = new Receiver()
                    .setUserId(u.getId())
                    .setTargetUserTenantId(tenantId);
            receivers.add(receiver);
        });

        logger.info("tenantId:[{}], receivers:[{}]", tenantId, receivers);

        // 异步发送站内消息
        messageClient.async().sendWebMessage(tenantId, APPLICANT_TEMPLATE_CODE, lang, receivers, args);
    }
}