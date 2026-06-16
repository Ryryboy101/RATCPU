module ALU (
    input logic [7:0] a,
    b,
    constants::ALUSelection operation,
    output logic [7:0] q,
    output logic [1:0] flags,
    output logic carry
);

  logic [7:0] aluA, aluB;
  logic cin;
  logic [7:0] adder_sum;
  logic adder_cout;

  always_comb begin : selector
    aluA = a;
    aluB = b;
    cin  = 0;
    q    = 8'b0;

    unique case (operation)
      constants::add_op: begin
        aluA = a;
        aluB = b;
        cin  = 0;
        q    = adder_sum;
      end
      constants::sub_op: begin
        aluA = a;
        aluB = ~b;
        cin  = 1;
        q    = adder_sum;
      end

      constants::xor_op:  q = a ^ b;
      constants::xnor_op: q = ~(a ^ b);
      constants::or_op:   q = a | b;
      constants::nor_op:  q = ~(a | b);
      constants::and_op:  q = a & b;
      constants::nand_op: q = ~(a & b);
    endcase
  end

  always_comb begin
    if ((operation == constants::add_op) || (operation == constants::sub_op)) begin
      carry = adder_cout;
    end else begin
      carry = 1'b0;
    end
  end

  Adder adder (
      .a(aluA),
      .b(aluB),
      .cin(cin),
      .sum(adder_sum),
      .cout(adder_cout),
      .flags(flags)
  );

endmodule
