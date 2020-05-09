package com.yjj.study.chain;

public class OldTest {

    public static void main(String[] args) {

//        UserInfoValidate userInfoValidate = new UserInfoValidate();
//        MobileValidate mobileValidate = new MobileValidate();
//        AddressValidate addressValidate = new AddressValidate();
////        userInfoValidate.setNext(mobileValidate);
//        mobileValidate.setNext(addressValidate);
//        addressValidate.setNext(userInfoValidate);

        LoginChain.Builder builder = new LoginChain.Builder();
        LoginChain login = builder
                .addValidate(new MobileValidate())
                .addValidate(new UserInfoValidate())
                .addValidate(new AddressValidate())
                .builder();


        Member member = new Member(" zhangsan", "abc213", "18607957182", "深圳");
        login.login(member);

    }

}
