package org.hrds.rducm.gitlab.infra.client.gitlab.api.admin;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.hrds.rducm.gitlab.infra.client.gitlab.Gitlab4jClientWrapper;
import org.hrds.rducm.gitlab.infra.client.gitlab.constant.GitlabClientConstants;
import org.hrds.rducm.gitlab.infra.client.gitlab.exception.GitlabClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 使用管理员权限调用的api
 *
 * @author ying.xie@hand-china.com
 * @date 2020/3/30
 */
@Repository
public class GitlabAdminApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabAdminApi.class);

    private final Gitlab4jClientWrapper gitlab4jClient;

    public GitlabAdminApi(Gitlab4jClientWrapper gitlab4jClient) {
        this.gitlab4jClient = gitlab4jClient;
    }

    /**
     * 获取Gitlab项目
     *
     * @param projectId Gitlab项目id
     * @return 如果未找到, 返回null
     */
    public Project getProject(Integer projectId) {
        try {
            return gitlab4jClient.getAdminGitLabApi()
                    .getProjectApi()
                    .getProject(projectId);
        } catch (GitLabApiException e) {
            // Gitlab查询到不存在的资源会返回404
            if (e.getHttpStatus() == HttpStatus.NOT_FOUND.value()) {
                LOGGER.warn(e.getMessage(), e);
                return null;
            } else {
                throw new GitlabClientException(e, e.getMessage());
            }
        }
    }

    public List<Member> getAllMembers(Integer projectId) {
        try {
            // 需要查询所有成员
            return gitlab4jClient.getAdminGitLabApi()
                    .getProjectApi()
                    .getAllMembers(projectId, GitlabClientConstants.DEFAULT_PER_PAGE, null)
                    .all();
        } catch (GitLabApiException e) {
            throw new GitlabClientException(e, e.getMessage());
        }
    }
}
