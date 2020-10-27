<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="建筑物id" prop="buildingid">
      <el-input v-model="dataForm.buildingid" placeholder="建筑物id"></el-input>
    </el-form-item>
    <el-form-item label="热点位置" prop="xstart">
      <el-input v-model="dataForm.xstart" placeholder="热点位置"></el-input>
    </el-form-item>
    <el-form-item label="热点名称" prop="hotpointname">
      <el-input v-model="dataForm.hotpointname" placeholder="热点名称"></el-input>
    </el-form-item>
    <el-form-item label="热点描述" prop="comment">
      <el-input v-model="dataForm.comment" placeholder="热点描述"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态"></el-input>
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
          id: 0,
          userid: '',
          buildingid: '',
          xstart: '',
          ystart: '',
          xend: '',
          yend: '',
          hotpointname: '',
          comment: '',
          status: '0'
        },
        dataRule: {
          buildingid: [
            { required: true, message: '建筑物id不能为空', trigger: 'blur' }
          ],
          xstart: [
            { required: true, message: '热点位置不能为空', trigger: 'blur' }
          ],
          hotpointname: [
            { required: true, message: '热点名称不能为空', trigger: 'blur' }
          ],
          comment: [
            { required: false, message: '热点描述不能为空', trigger: 'blur' }
          ]
        },
        buildingList: []
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storagehotpoints/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userid = data.storageHotPoints.userid
                this.dataForm.buildingid = data.storageHotPoints.buildingid
                this.dataForm.xstart = data.storageHotPoints.xstart
                this.dataForm.ystart = data.storageHotPoints.ystart
                this.dataForm.xend = data.storageHotPoints.xend
                this.dataForm.yend = data.storageHotPoints.yend
                this.dataForm.hotpointname = data.storageHotPoints.hotpointname
                this.dataForm.comment = data.storageHotPoints.comment
                this.dataForm.status = data.storageHotPoints.status
              }
            })
          }
          this.$http({
            url: this.$http.adornUrl(`/storage/storagebuilding/qryUserBuildings`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.buildingList = data.buildings
              console.log(this.buildingList)
            }
          })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/storage/storagehotpoints/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userid': this.dataForm.userid,
                'buildingid': this.dataForm.buildingid,
                'xstart': this.dataForm.xstart,
                'ystart': this.dataForm.ystart,
                'xend': this.dataForm.xend,
                'yend': this.dataForm.yend,
                'hotpointname': this.dataForm.hotpointname,
                'comment': this.dataForm.comment,
                'status': this.dataForm.status
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
