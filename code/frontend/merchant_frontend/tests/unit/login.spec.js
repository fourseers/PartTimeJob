import {shallowMount,mount} from '@vue/test-utils'
import Login from '@/views/Login.vue'
describe('Login.vue', () => {
    describe('Test for  Button Component', () => {
        const wrapper = shallowMount(Login);

        it('calls handleSubmit(\'formInline\') when click on button', () => {
            // 创建mock函数
            const mockFn = jest.fn();
            // 设置 Wrapper vm 的方法并强制更新。
            wrapper.setMethods({
                handleSubmit: mockFn
            });
            // 触发按钮的点击事件
            wrapper.find('button').trigger('click');
            expect(mockFn).toBeCalled();
            expect(mockFn).toHaveBeenCalledTimes(1)
        })
    });
})

describe('Login.vue', () => {
    it("Dom correct  ", async () => {
        const wrapper = mount(Login);

        const form = wrapper.find({ ref:"formInline" }) ;
        expect(form.is('Form')).toBe(true);
        const user = wrapper.find( "#name") ;
        expect(user.is('Input')).toBe(true);
        const password = wrapper.find( "#password") ;
        expect(password.is('Input')).toBe(true);
    })
})


import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

describe('Login.vue', () => {
    it('returns data when sendMessage is called', done => {
        var mock = new MockAdapter(axios);

        const goodresponse = { response:{
                message: 'success'
            }};

        // 模拟成功请求
        mock.onPost( 'http://202.120.40.8:30552/auth/merchant/login').reply(200,  goodresponse );

        //模拟登录                POST

        Login.methods.login("user_one", "user_one").then(response => {
            expect(response).toEqual( goodresponse );
            done();
        });

    })
});

//
// describe('Login.vue', () => {
//     it('test for error handling', done => {
//         var mock = new MockAdapter(axios);
//
//         mock.onPost( 'http://202.120.40.8:30552/auth/merchant/login').networkError()
//
//         //模拟登录                POST
//
//         Login.methods.login("user_one", "user_one").then(response => {
//
//             expect(response).toEqual({});
//             done();
//         });
//
//     })
// })
