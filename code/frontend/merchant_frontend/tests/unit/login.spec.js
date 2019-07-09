import { shallowMount } from '@vue/test-utils'
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

jest.mock('axios', () => ({
    post: jest.fn()
}));

import axios from 'axios';

describe('Test for axios post', () => {
    let wrapper;

    beforeEach(() => {
        axios.post.mockClear();
        wrapper = shallowMount(Login);
    });

    afterEach(() => {
        wrapper.destroy();
    });

    // 点击按钮后调用了 handleSubmit 方法
    it('login Fn should be called', () => {
        const mockFn = jest.fn();
        wrapper.setMethods({handleSubmit: mockFn});
        wrapper.find('button').trigger('click');
        expect(mockFn).toBeCalled();
    });

    // 点击按钮后调用了axios.post方法
    it('axios.post Fn should be called', () => {
        const URL =  "http://202.120.40.8:30552";
        wrapper.find('button').trigger('click');
        expect(axios.post).toBeCalledWith(URL)
    });
});
