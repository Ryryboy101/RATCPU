module Adder (
    input logic [7:0] a,
    b,
    logic cin,
    output logic signed [7:0] sum,
    logic cout,
    logic [1:0] flags
);


  logic [2:0] carryChain;
  assign carryChain[0] = cin;

  genvar i;
  generate
    for (i = 0; i < 2; i = i + 1) begin : cla_loop
      CLA4Bit block (
          .a(a[(i*4)+3 : (i*4)]),
          .b(b[(i*4)+3 : (i*4)]),
          .cin(carryChain[i]),
          .sum(sum[(i*4)+3 : (i*4)]),
          .cout(carryChain[i+1])
      );
    end
  endgenerate

  assign cout = carryChain[$size(carryChain)-1];
  always_comb begin : checkFlags
    flags[0] = 1'b0;
    flags[1] = 1'b0;

    if (sum < 0) begin
      flags[0] = 1'b1;
    end

    if (sum == 0) begin
      flags[1] = 1'b1;
    end

  end

endmodule
