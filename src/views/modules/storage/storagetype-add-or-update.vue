<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="类型的显示名称" prop="typename">
      <el-input v-model="dataForm.typename" placeholder="类型的显示名称"></el-input>
    </el-form-item>
    <el-form-item label="父类型id,如果为空说明是父类型" prop="parenttypeid">
      <el-input v-model="dataForm.parenttypeid" placeholder="父类型id,如果为空说明是父类型"></el-input>
    </el-form-item>
    <el-form-item label="" prop="typecomment">
      <el-input v-model="dataForm.typecomment" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          typeid: 0,
          typename: '',
          parenttypeid: '',
          typecomment: ''
        },
        dataRule: {
          typename: [
            { required: true, message: '类型的显示名称不能为空', trigger: 'blur' }
          ],
          parenttypeid: [
            { required: true, message: '父类型id,如果为空说明是父类型不能为空', trigger: 'blur' }
          ],
          typecomment: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.typeid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.typeid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storagetype/info/${this.dataForm.typeid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.typename = data.storageType.typename
                this.dataForm.parenttypeid = data.storageType.parenttypeid
                this.dataForm.typecomment = data.storageType.typecomment
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storagetype/${!this.dataForm.typeid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'typeid': this.dataForm.typeid || undefined,
                'typename': this.dataForm.typename,
                'parenttypeid': this.dataForm.parenttypeid,
                'typecomment': this.dataForm.typecomment
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
