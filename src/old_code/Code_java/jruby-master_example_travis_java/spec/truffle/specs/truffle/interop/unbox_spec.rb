# Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved. This
# code is released under a tri EPL/GPL/LGPL license. You can use it,
# redistribute it and/or modify it under the terms of the:
# 
# Eclipse Public License version 1.0
# GNU General Public License version 2
# GNU Lesser General Public License version 2.1

require_relative '../../../../ruby/spec_helper'

describe "Truffle::Interop.unbox" do
    
  it "passes through fixnums" do
    Truffle::Interop.unbox(14).should == 14
  end
  
  it "passes through floats" do
    Truffle::Interop.unbox(14.2).should == 14.2
  end
  
  it "passes through true" do
    Truffle::Interop.unbox(true).should == true
  end
  
  it "passes through false" do
    Truffle::Interop.unbox(false).should == false
  end
  
  it "doesn't work on empty strings" do
    lambda { Truffle::Interop.unbox('') }.should raise_error(RubyTruffleError)
  end
    
  it "returns the first byte on strings with one byte" do
    Truffle::Interop.unbox('1').should == '1'.ord
  end
    
  it "returns the first byte on strings with two bytes" do
    Truffle::Interop.unbox('1').should == '1'.ord
  end
    
  it "returns nil for nil" do
    Truffle::Interop.unbox(nil).should be_nil
  end

end
