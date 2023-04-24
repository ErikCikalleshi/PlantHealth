<template>
    <v-dialog v-model="addUserDialog" persistent width="1024">
        <template v-slot:activator="{ props }">
            <v-btn icon="mdi-account-plus-outline" v-bind="props" class="mx-5"></v-btn>
        </template>
        <v-card>
            <v-card-title>
                <span class="text-xl">Create new user</span>
            </v-card-title>
            <v-form ref="addUserForm">
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field label="Username*" required
                                              :rules="[v => !!v || 'Item is required']"
                                              v-model="newUser.username"/>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field label="Firstname*" required
                                              :rules="[v => !!v || 'Item is required']"
                                              v-model="newUser.firstname"/>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field label="Lastname*" required
                                              :rules="[v => !!v || 'Item is required']"
                                              v-model="newUser.lastname"/>
                            </v-col>
                            <v-col cols="12">
                                <v-text-field label="Email*" required v-model="newUser.email"
                                              :rules="[v => !!v || 'Item is required']"/>
                            </v-col>
                            <v-col cols="12" sm="6">
                                <v-text-field label="Password*" type="password" required
                                              v-model="password" :rules="passwordRules"/>
                            </v-col>
                            <v-col cols="12" sm="6">
                                <v-text-field label="Confirm password*" type="password" required
                                              v-model=" confirmPassword" :rules="confirmPasswordRules"/>
                            </v-col>
                            <v-col cols="12" sm="6">
                                <v-select
                                        clearable
                                        chips
                                        label="Select roles*"
                                        :items="['USER', 'GARDENER', 'ADMIN']"
                                        :rules="[v => v.length>0 || 'Select at least one role']"
                                        v-model="newUser.roles"
                                        multiple
                                        required
                                ></v-select>
                            </v-col>
                        </v-row>
                    </v-container>

                    <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="error" variant="flat"
                           @click="resetNewUser(); addUserDialog = false">
                        Cancel
                    </v-btn>
                    <v-btn color="primary" variant="flat" @click="addNewUser()">Save</v-btn>
                </v-card-actions>
            </v-form>
        </v-card>
    </v-dialog>
</template>

<script lang="ts">

import {defineComponent} from "vue";
import type IUser from "@/interfaces/user/IUser";
import AdminUserService from "@/services/admin/AdminUserService";

export default defineComponent({
    name: "addUsersDialogForm",
    props: {
        users: {
            type: Array<IUser>,
            required: false
        },
    },
    data() {
        return {
            addUserDialog: false,
            password: '',
            confirmPassword: '',
            newUser: {
                firstname: '',
                lastname: '',
                created: '',
                username: '',
                email: '',
                roles: ['USER'],
            } as IUser,
            passwordRules: [
                (v: any) => !!v || 'required',
                (v: any) => (v && v.length >= 8) || 'Min 8 characters',
                (v: any) => (v && v.length <= 20) || 'Max 20 characters',
                (v: any) => (v && /[A-Z]/.test(v)) || 'One uppercase letter required',
                (v: any) => (v && /[a-z]/.test(v)) || 'One lowercase letter required',
                (v: any) => (v && /[0-9]/.test(v)) || 'One number required',
            ],
            confirmPasswordRules:[
                (v: any) => !!v || 'required',
                (v:any) => v === this.password || 'Passwords must match'
            ]
        }
    },
    methods: {
        resetNewUser() {
            this.newUser = {
                firstname: '',
                lastname: '',
                created: '',
                username: '',
                email: '',
                roles: ['USER'],
            } as IUser
            this.password = '';
            this.confirmPassword = '';
        },
        required(v: any) {
            return !!v || 'Field is required'
        },
        async addNewUser() {
            const form = this.$refs.addUserForm as { validate: () => Promise<{ valid: boolean }> };
            const {valid} = await form.validate();
            if (valid) {
                AdminUserService.createNewUser(this.newUser, this.password).then((response) => {
                    if (response.status===201){
                        const user:IUser = response.data;
                        if (this.users){
                            this.users.push(user);
                        }
                        this.addUserDialog = false;
                        this.resetNewUser();
                    }
                })
            }
        }
    },
})
</script>