<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="类型的显示名称" prop="tagname">
      <el-input v-model="dataForm.tagname" placeholder="类型的显示名称"></el-input>
    </el-form-item>
    <el-form-item label="" prop="tagcomment">
      <el-input v-model="dataForm.tagcomment" placeholder=""></el-input>
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
          tagid: 0,
          tagname: '',
          tagcomment: ''
        },
        dataRule: {
          tagname: [
            { required: true, message: '类型的显示名称不能为空', trigger: 'blur' }
          ],
          tagcomment: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.tagid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.tagid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storagetag/info/${this.dataForm.tagid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.tagname = data.storageTag.tagname
                this.dataForm.tagcomment = data.storageTag.tagcomment
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
              url: this.$http.adornUrl(`/storage/storagetag/${!this.dataForm.tagid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'tagid': this.dataForm.tagid || undefined,
                'tagname': this.dataForm.tagname,
                'tagcomment': this.dataForm.tagcomment
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
