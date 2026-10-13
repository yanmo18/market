<template>
  <div>
    <!-- 搜索框 -->
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入商品名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button @click="load" class="ml-5" type="primary">搜索</el-button>
      <i>
        <el-button class="ml-5" @click="reset" type="warning">重置</el-button>
      </i>
    </div>
    <!-- 搜索框 -->

    <!-- 增加与删除 -->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">
        新增
        <i class="el-icon-circle-plus-outline"></i>
      </el-button>

      <el-popconfirm class="ml-5" confirm-button-text="確定" cancel-button-text="我在想想" icon="el-icon-info"
        icon-color="red" title="您確定批量删除數據吗？" @confirm="delBatch">
        <el-button type="danger" slot="reference">
          批量删除
          <i class="el-icon-remove-outline"></i>
        </el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
      @selection-change="handleSelectionChange">
      >
      <el-table-column align="center" type="selection" width="55"></el-table-column>

      <el-table-column align="center" prop="id" label="ID" width="80"></el-table-column>

      <el-table-column width="150" align="center" prop="name" label="商品名称"></el-table-column>

      <el-table-column width="150" align="center" prop="price" label="商品价格"></el-table-column>

      <el-table-column width="150" align="center" prop="typeName" label="类型"></el-table-column>

      <el-table-column width="150" align="center" prop="store" label="库存"></el-table-column>
      <!--          <el-table-column prop="picture" label="商品图片"></el-table-column>-->
      <el-table-column width="150" align="center" label="商品图片">
        <template slot-scope="scope">
          <img :src="scope.row.picture" style="width: 95px; height: 100px" />
        </template>
      </el-table-column>

      <el-table-column width="150" align="center" prop="company" label="生产公司"></el-table-column>

      <el-table-column width="150" align="center" prop="madein" label="产地"></el-table-column>

      <el-table-column width="150" :show-overflow-tooltip="true" prop="info" label="商品简介"
        style="width: 95px; height: 100px"></el-table-column>

      <el-table-column fixed="right" label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="success">
            编辑
            <i class="el-icon-edit"></i>
          </el-button>

          <!-- 刪除確認框 -->
          <el-popconfirm class="ml-5" confirm-button-text="確定" cancel-button-text="我在想想" icon="el-icon-info"
            icon-color="red" title="您確定删除吗？" @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">
              删除
              <i class="el-icon-remove-outline"></i>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="padding: 10px 0">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
        :page-sizes="[2, 5, 10]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total"></el-pagination>
    </div>

    <!-- 弹窗 -->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="35%">
      <el-form label-width="100px" size="smail">
        <el-form-item label="商品名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品价格">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品库存">
          <el-input v-model="form.store" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="生产公司">
          <el-input v-model="form.company" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="产地">
          <el-input v-model="form.madein" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="商品简介">
          <el-input v-model="form.info" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="类型名称">
          <el-select v-model="form.typeName" placeholder="请选择" @change="changeSelect">
            <el-option v-for="item in type" :key="item.id" :label="item.type" :value="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="商品图片">
          <el-upload class="avatar-uploader" action="http://localhost:9090/file/upload" :show-file-list="false"
            :on-success="handleAvatarSuccess">
            <img style="width: 150px; height: 150px" v-if="form.picture" :src="form.picture" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      type: [],
      dialogFormVisible: false, //默认不展示
      form: {},
      multipleSelection: [] //批量刪除
    };
  },
  created() {
    //请求分页查询数据
    this.load();
    this.typeInfo();
  },
  methods: {
    changeSelect(id) {
      this.type.forEach(element => {
        if (element.id == id) {
          this.form.typeId = element.id;
          this.form.typeName = element.type;
          console.log(this.form);
        }
      });
    },
    typeInfo() {
      this.request.get("/type/all").then(res => {
        if (res.code === "200") {
          this.type = res.data;
        } else {
          this.$message.error("数据请求失败");
        }
      });
    },
    load() {
      this.request
        .get("/product/page", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            name: this.name
          }
        })
        .then(res => {
          console.log(res);
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },

    save() {
      this.request.post("/product/save", this.form).then(res => {
        if (res.code === "200") {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;

          this.load(); //刷新数据
        } else {
          this.$message.error("保存失败");
        }
      });
    },
    handleAdd() {
      //增加用户
      this.dialogFormVisible = true;
      this.form = {};
    },

    handleEdit(row) {
      //修改用户
      this.form = row;
      this.dialogFormVisible = true;
    },

    del(id) {
      //删除用户
      this.request.delete("/product/" + id).then(res => {
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.load(); //刷新数据
        } else {
          this.$message.error("删除失败");
        }
      });
    },

    handleSelectionChange(val) {
      //批量刪除
      console.log(val);
      this.multipleSelection = val;
    },
    delBatch() {
      //批量刪除
      let ids = this.multipleSelection.map(v => v.id);
      this.request.post("/product/del/batch", ids).then(res => {
        if (res.code === "200") {
          this.$message.success("批量删除成功");
          this.load(); //刷新数据
        } else {
          this.$message.error("批量删除失败");
        }
      });
    },

    reset() {
      (this.name = ""), this.load();
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },

    handleAvatarSuccess(res) {
      console.log(res);
      this.form.picture = res;
    }
  }
};
</script>

<style>
.headerBg {
  background-color: #eee !important;
}
</style>