package com.course.model;

public class LoginDataModel  {

        private String userId;
        private String password;
        private String oldPassword;


        public String getOldPassword()
        {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword)
        {
            this.oldPassword = oldPassword;
        }

        public String getUserId()
        {
            return userId;
        }

        public void setUserId(String userId)
        {
            this.userId = userId;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }



    }


