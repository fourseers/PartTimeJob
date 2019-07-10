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
