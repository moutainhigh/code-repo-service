import React, { createContext, useMemo } from 'react';
import { DataSet } from 'choerodon-ui/pro';
import { inject } from 'mobx-react';
import { injectIntl } from 'react-intl';
import RoleAssignDataSet from './RoleAssignDataSet';

const Store = createContext();

export default Store;

export const SiderStoreProvider = injectIntl(inject('AppState')((props) => {
  const { AppState: { currentMenuType: { id, organizationId }, getUserId: userId }, intl, children } = props;
  const roleAssignDataSet = useMemo(() => new DataSet(RoleAssignDataSet({ id, intl })), [id]);

  const intlPrefix = 'organization.user.sider';
  const dsStore = [];
  const value = {
    ...props,
    prefixCls: 'code-management-impoet-member',
    intlPrefix,
    organizationId,
    roleAssignDataSet,
    userId,
    dsStore,
  };
  return (
    <Store.Provider value={value}>
      {children}
    </Store.Provider>
  );
}));
