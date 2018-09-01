package com.xingguang.master.maincode.home.model;

import java.util.List;

/**
 * 创建日期：2018/6/20
 * 描述:部门工种
 * 作者:LiuYu
 */
public class BuMengBean {


    /**
     * status : 1
     * msg : SUCCESS
     * data : [{"ID":1,"Name":"安监局","data":[{"ID":10,"Name":"安监局电工"},{"ID":11,"Name":"安监局瓦工"},{"ID":12,"Name":"安监局技工"},{"ID":21,"Name":"安监局测试试题"}]},{"ID":2,"Name":"质监局","data":[{"ID":13,"Name":"质监局电工"},{"ID":14,"Name":"质监局瓦工"},{"ID":16,"Name":"质监局技工"},{"ID":17,"Name":"质监局技工01"}]}]
     */

    private int status;
    private String msg;
    private List<DataBeanX> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * ID : 1
         * Name : 安监局
         * data : [{"ID":10,"Name":"安监局电工"},{"ID":11,"Name":"安监局瓦工"},{"ID":12,"Name":"安监局技工"},{"ID":21,"Name":"安监局测试试题"}]
         */

        private int ID;
        private String Name;
        private List<DataBean> data;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * ID : 10
             * Name : 安监局电工
             */

            private int ID;
            private String Name;
            private String ClassPic;
            private String PIC;
            private int IsEnabled;

            public int getIsEnabled() {
                return IsEnabled;
            }

            public void setIsEnabled(int isEnabled) {
                IsEnabled = isEnabled;
            }

            public String getPIC() {
                return PIC;
            }

            public void setPIC(String PIC) {
                this.PIC = PIC;
            }

            public String getClassPic() {
                return ClassPic;
            }

            public void setClassPic(String classPic) {
                ClassPic = classPic;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }
}
